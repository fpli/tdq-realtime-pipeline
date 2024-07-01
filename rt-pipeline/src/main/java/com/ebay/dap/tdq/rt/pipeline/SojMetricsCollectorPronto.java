package com.ebay.dap.tdq.rt.pipeline;

import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.connector.pronto.ProntoEnv;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import com.ebay.dap.tdq.rt.domain.SojMetric;
import com.ebay.dap.tdq.rt.domain.pronto.SojMetricDoc;
import com.ebay.dap.tdq.rt.function.SojMetricAggFunction;
import com.ebay.dap.tdq.rt.function.SojMetricDocMapFunction;
import com.ebay.dap.tdq.rt.function.SojMetricProcessWindowFunction;
import com.ebay.dap.tdq.rt.key.SimpleSojEventKeySelector;
import com.ebay.dap.tdq.rt.sink.SojMetricProntoEmitter;
import com.ebay.dap.tdq.rt.source.SimpleSojEventDeserializationSchema;
import com.ebay.dap.tdq.rt.watermark.SimpleSojEventTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.elasticsearch.sink.Elasticsearch7SinkBuilder;
import org.apache.flink.connector.elasticsearch.sink.ElasticsearchSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;
import org.apache.http.HttpHost;

import java.time.Duration;

import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.RHEOS_REGISTRY_URL;

public class SojMetricsCollectorPronto {

    public static void main(String[] args) throws Exception {

        FlinkEnv flinkEnv = new FlinkEnv(args);
        StreamExecutionEnvironment executionEnvironment = flinkEnv.init();

        // DO NOT change uid once they are used in prod, otherwise flink might not able to resume from savepoint
        final String sourceOpUid = "kafka_source";
        final String windowOpUid = "metric_agg";
        final String sinkOpUid = "pronto_sink";
        final String lateSinkOpUid = "hdfs_sink";
        final String lateMapOpUid = "late_event_map";
        final String sherlockSinkOpUid = "sherlock_sink";

        // operator names
        final String sourceOpName = "Kafka - behavior.totalv3";
        final String windowOpName = "Tumbling Window - Hourly Page Metric Agg";
        final String sinkOpName = "Pronto";
        final String lateSinkOpName = "Late Event";
        final String lateMapOpName = "Late Event Map";
        final String sherlockSinkOpName = "Sherlock";
        final String lateEventFlag = "late";
        final String nonLateEventFlag = "nonLate";

        final int WINDOW_MINS = 60;
        final int maxOutOfOrderness = flinkEnv.getInteger(FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN);
        final int idleTimeout = flinkEnv.getInteger(FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN);
        final String registryUrl = flinkEnv.getString(RHEOS_REGISTRY_URL);

        KafkaSource<SimpleSojEvent> kafkaSource = KafkaSource.<SimpleSojEvent>builder()
                                                             .setBootstrapServers(flinkEnv.getSourceKafkaBrokers())
                                                             .setGroupId(flinkEnv.getSourceKafkaGroupId())
                                                             .setTopics(flinkEnv.getSourceKafkaTopics())
                                                             .setProperties(flinkEnv.getKafkaConsumerProps())
                                                             .setStartingOffsets(flinkEnv.getSourceKafkaStartingOffsets())
                                                             .setDeserializer(new SimpleSojEventDeserializationSchema(registryUrl))
                                                             .build();

        WatermarkStrategy<SimpleSojEvent> watermarkStrategy = WatermarkStrategy.<SimpleSojEvent>forBoundedOutOfOrderness(Duration.ofMinutes(maxOutOfOrderness))
                                                                               .withTimestampAssigner(new SimpleSojEventTimestampAssigner())
                                                                               .withIdleness(Duration.ofMinutes(idleTimeout));


        SingleOutputStreamOperator<SimpleSojEvent> sourceStream =
                executionEnvironment.fromSource(kafkaSource, watermarkStrategy, sourceOpName)
                                    .uid(sourceOpUid)
                                    .setParallelism(flinkEnv.getSourceParallelism());


        OutputTag<SimpleSojEvent> lateEventOutputTag = new OutputTag<>("late-simple-sojevent", TypeInformation.of(SimpleSojEvent.class));


        SingleOutputStreamOperator<SojMetric> windowStream =
                sourceStream.keyBy(new SimpleSojEventKeySelector())
                            .window(TumblingEventTimeWindows.of(Time.minutes(WINDOW_MINS)))
                            .sideOutputLateData(lateEventOutputTag)
                            .aggregate(new SojMetricAggFunction(), new SojMetricProcessWindowFunction())
                            .name(windowOpName)
                            .uid(windowOpUid)
                            .setParallelism(flinkEnv.getSourceParallelism());

        ProntoEnv prontoEnv = flinkEnv.getProntoEnv();
        HttpHost prontoHost = new HttpHost(prontoEnv.getHost(), prontoEnv.getPort(), prontoEnv.getScheme());

        // use an ElasticsearchSink.Builder to create an ElasticsearchSink
        ElasticsearchSink<SojMetricDoc> prontoSink = new Elasticsearch7SinkBuilder<SojMetricDoc>()
                .setBulkFlushMaxActions(1) // Instructs the sink to emit after every element, otherwise they would be buffered
                .setHosts(prontoHost)
                .setEmitter(new SojMetricProntoEmitter("my-index"))
                .build();

//        esSinkBuilder.setRestClientFactory(restClientBuilder -> {
//            restClientBuilder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Content-Type", "application/json")});
//            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
//                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(prontoEnv.getUsername(), prontoEnv.getPassword()));
//                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            });
//        });
//
//        // configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered
//        esSinkBuilder.setBulkFlushMaxActions(1);
//        esSinkBuilder.setBulkFlushBackoff(true);
//        esSinkBuilder.setBulkFlushBackoffRetries(3);
//        esSinkBuilder.setBulkFlushBackoffType(ElasticsearchSinkBase.FlushBackoffType.CONSTANT);
//        esSinkBuilder.setBulkFlushBackoffDelay(3000);
//
        windowStream.map(new SojMetricDocMapFunction())
                    .name("SojMetric -> SojMetricDoc")
                    .uid("soj-metric-doc-map")
                    .setParallelism(100)
                    .sinkTo(prontoSink)
                    .name("Pronto Sink")
                    .uid("pronto-sink")
                    .setParallelism(100);
//
//        StreamingFileSink<SimpleSojEvent> hdfsSink = StreamingFileSink
//                .forBulkFormat(new Path(flinkEnv.getString(FLINK_APP_SINK_HDFS_PATH)),
//                        ParquetAvroWritersWithCompression.forReflectRecord(SimpleSojEvent.class))
//                .withBucketAssigner(new LateSimpleSojEventBucketAssigner())
//                .build();
//
//        windowStream.addSink(new SherlockEventSinkFunction(
//                            flinkEnv.getString(SHERLOCK_ENDPOINT),
//                            flinkEnv.getString(SHERLOCK_APPLICATION_ID),
//                            flinkEnv.getString(SHERLOCK_NAMESPACE),
//                            flinkEnv.getString(SHERLOCK_SCHEMA),
//                            flinkEnv.getString(SHERLOCK_LABEL),
//                            nonLateEventFlag))
//                    .name(sherlockSinkOpName)
//                    .uid(sherlockSinkOpUid)
//                    .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));
//
//        // late event also send to sherlock
//        windowStream.getSideOutput(lateEventOutputTag)
//                    //  recalculate the window time or use event time?
//                    .map(new LateEventToMetricsMapFunction(WINDOW_MINS, false))
//                    .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE))
//                    .name(lateMapOpName)
//                    .uid(lateMapOpUid)
//                    .addSink(new SherlockEventSinkFunction(
//                            flinkEnv.getString(SHERLOCK_ENDPOINT),
//                            flinkEnv.getString(SHERLOCK_APPLICATION_ID),
//                            flinkEnv.getString(SHERLOCK_NAMESPACE),
//                            flinkEnv.getString(SHERLOCK_SCHEMA),
//                            flinkEnv.getString(SHERLOCK_LABEL),
//                            lateEventFlag
//                    ))
//                    .name(lateSinkOpName)
//                    .uid(lateSinkOpUid)
//                    .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));

        // submit flink job
        flinkEnv.execute(executionEnvironment);

    }

}
