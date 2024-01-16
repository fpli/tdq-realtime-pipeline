package com.ebay.dap.tdq.rt.pipeline;

import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import com.ebay.dap.tdq.rt.function.LateEventToMetricsMapFunction;
import com.ebay.dap.tdq.rt.function.MetricAggFunction;
import com.ebay.dap.tdq.rt.function.MetricProcessWindowFunction;
import com.ebay.dap.tdq.rt.function.SherlockEventSinkFunction;
import com.ebay.dap.tdq.rt.function.SimpleSojEventDeserializationSchema;
import com.ebay.dap.tdq.rt.watermark.SimpleSojEventTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_PARALLELISM_SOURCE;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.RHEOS_REGISTRY_URL;
import static com.ebay.dap.tdq.common.constant.Property.SHERLOCK_APPLICATION_ID;
import static com.ebay.dap.tdq.common.constant.Property.SHERLOCK_ENDPOINT;
import static com.ebay.dap.tdq.common.constant.Property.SHERLOCK_LABEL;
import static com.ebay.dap.tdq.common.constant.Property.SHERLOCK_NAMESPACE;
import static com.ebay.dap.tdq.common.constant.Property.SHERLOCK_SCHEMA;

public class PageMetricCollector {

    private static final int WINDOW_MINS = 30;

    public static void main(String[] args) throws Exception {

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
        final boolean lateEventFlag = true;
        final boolean nonLateEventFlag = false;

        FlinkEnv flinkEnv = new FlinkEnv(args);

        StreamExecutionEnvironment executionEnvironment = flinkEnv.init();

        KafkaSource<SimpleSojEvent> kafkaSource = KafkaSource.<SimpleSojEvent>builder()
                .setBootstrapServers(flinkEnv.getKafkaSourceBrokers())
                .setGroupId(flinkEnv.getKafkaSourceGroupId())
                .setTopics(flinkEnv.getKafkaSourceTopics())
                .setProperties(flinkEnv.getKafkaConsumerProps())
                .setStartingOffsets(flinkEnv.getKafkaSourceStartingOffsets())
                .setDeserializer(new SimpleSojEventDeserializationSchema(flinkEnv.getString(RHEOS_REGISTRY_URL)))
                .build();

        Integer maxOutOfOrderness = flinkEnv.getInteger(FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN);
        Integer idleTimeout = flinkEnv.getInteger(FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN);

        WatermarkStrategy<SimpleSojEvent> watermarkStrategy = WatermarkStrategy.<SimpleSojEvent>forBoundedOutOfOrderness(Duration.ofMinutes(maxOutOfOrderness))
                .withTimestampAssigner(new SimpleSojEventTimestampAssigner())
                .withIdleness(Duration.ofMinutes(idleTimeout));


        SingleOutputStreamOperator<SimpleSojEvent> sourceStream = executionEnvironment.fromSource(kafkaSource, watermarkStrategy, sourceOpName)
                .uid(sourceOpUid)
                .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));


        OutputTag<SimpleSojEvent> lateEventOutputTag = new OutputTag<>("late-simple-sojevent", TypeInformation.of(SimpleSojEvent.class));


        SingleOutputStreamOperator<PageMetric> windowStream = sourceStream.keyBy(SimpleSojEvent::getPageId)
                .window(TumblingEventTimeWindows.of(Time.minutes(WINDOW_MINS)))
                .sideOutputLateData(lateEventOutputTag)
                .aggregate(new MetricAggFunction(), new MetricProcessWindowFunction())
                .name(windowOpName)
                .uid(windowOpUid)
                .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));

//        ProntoEnv prontoEnv = flinkEnv.getProntoEnv();
//        List<HttpHost> httpHosts = Collections.singletonList(new HttpHost(
//                prontoEnv.getHost(), prontoEnv.getPort(), prontoEnv.getScheme()
//        ));
//
//        // use an ElasticsearchSink.Builder to create an ElasticsearchSink
//        ElasticsearchSink.Builder<PageMetric> esSinkBuilder = new ElasticsearchSink.Builder<>(
//                httpHosts, new PageMetricProntoSinkFunction("my-index")
//        );
//
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
//        windowStream.addSink(esSinkBuilder.build())
//                .name(sinkOpName)
//                .uid(sinkOpUid)
//                .setParallelism(1);
//
//        StreamingFileSink<SimpleSojEvent> hdfsSink = StreamingFileSink
//                .forBulkFormat(new Path(flinkEnv.getString(FLINK_APP_SINK_HDFS_PATH)),
//                        ParquetAvroWritersWithCompression.forReflectRecord(SimpleSojEvent.class))
//                .withBucketAssigner(new LateSimpleSojEventBucketAssigner())
//                .build();
//
        windowStream
                .addSink(new SherlockEventSinkFunction(
                        flinkEnv.getString(SHERLOCK_ENDPOINT),
                        flinkEnv.getString(SHERLOCK_APPLICATION_ID),
                        flinkEnv.getString(SHERLOCK_NAMESPACE),
                        flinkEnv.getString(SHERLOCK_SCHEMA),
                        flinkEnv.getString(SHERLOCK_LABEL),
                        nonLateEventFlag
                ))
                .name(sherlockSinkOpName)
                .uid(sherlockSinkOpUid)
                .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));

        // late event also send to sherlock
        windowStream.getSideOutput(lateEventOutputTag)
                //  recalculate the window time or use event time?
                .map(new LateEventToMetricsMapFunction(WINDOW_MINS, false))
                .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE))
                .name(lateMapOpName)
                .uid(lateMapOpUid)
                .addSink(new SherlockEventSinkFunction(
                        flinkEnv.getString(SHERLOCK_ENDPOINT),
                        flinkEnv.getString(SHERLOCK_APPLICATION_ID),
                        flinkEnv.getString(SHERLOCK_NAMESPACE),
                        flinkEnv.getString(SHERLOCK_SCHEMA),
                        flinkEnv.getString(SHERLOCK_LABEL),
                        lateEventFlag
                ))
                .name(lateSinkOpName)
                .uid(lateSinkOpUid)
                .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));

        // submit flink job
        flinkEnv.execute(executionEnvironment);

    }

}
