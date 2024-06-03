package com.ebay.dap.tdq.rt.pipeline;

import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.rt.domain.CJSMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import com.ebay.dap.tdq.rt.function.CJSMetricAggFunction;
import com.ebay.dap.tdq.rt.function.CJSMetricProcessWindowFunction;
import com.ebay.dap.tdq.rt.function.impl.SherlockEventSinkFunctionCJSMetric;
import com.ebay.dap.tdq.rt.key.SimpleSignalDeltaKeySelector;
import com.ebay.dap.tdq.rt.source.SimpleSignalDeltaDeserializationSchema;
import com.ebay.dap.tdq.rt.watermark.SimpleSignalDeltaTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSink;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

import static com.ebay.dap.tdq.common.constant.Property.*;

public class CJSMetricsCollector {

    public static void main(String[] args) throws Exception {

        // DO NOT change uid once they are used in prod, otherwise flink might not able to resume from savepoint
        final String sourceOpUid = "kafka_source";
        final String windowOpUid = "metric_agg";
        final String sinkOpUid = "pronto_sink";
        final String lateSinkOpUid = "hdfs_sink";
        final String lateMapOpUid = "late_event_map";
        final String sherlockSinkOpUid = "sherlock_sink";

        // operator names
        final String sourceOpName = "Kafka Source";
        final String windowOpName = "Tumbling Window - CJS Metric Agg";
        final String sinkOpName = "Pronto";
        final String lateSinkOpName = "Late Event";
        final String lateMapOpName = "Late Event Map";
        final String sherlockSinkOpName = "Sherlock";
        final String lateEventFlag = "late";
        final String nonLateEventFlag = "nonLate";

        final int WINDOW_MINS = 30;

        FlinkEnv flinkEnv = new FlinkEnv(args);

        StreamExecutionEnvironment executionEnvironment = flinkEnv.init();

        KafkaSource<SimpleSignalDelta> kafkaSource = KafkaSource.<SimpleSignalDelta>builder()
                                                                .setBootstrapServers(flinkEnv.getSourceKafkaBrokers())
                                                                .setGroupId(flinkEnv.getSourceKafkaGroupId())
                                                                .setTopics(flinkEnv.getSourceKafkaTopics())
                                                                .setProperties(flinkEnv.getKafkaConsumerProps())
                                                                .setStartingOffsets(flinkEnv.getSourceKafkaStartingOffsets())
                                                                .setDeserializer(new SimpleSignalDeltaDeserializationSchema())
                                                                .build();


        Integer maxOutOfOrderness = flinkEnv.getInteger(FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN);
        Integer idleTimeout = flinkEnv.getInteger(FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN);

        WatermarkStrategy<SimpleSignalDelta> watermarkStrategy = WatermarkStrategy.<SimpleSignalDelta>forBoundedOutOfOrderness(Duration.ofMinutes(maxOutOfOrderness))
                                                                                  .withTimestampAssigner(new SimpleSignalDeltaTimestampAssigner())
                                                                                  .withIdleness(Duration.ofMinutes(idleTimeout));


        SingleOutputStreamOperator<SimpleSignalDelta> sourceStream =
                executionEnvironment.fromSource(kafkaSource, watermarkStrategy, sourceOpName)
                                    .uid(sourceOpUid)
                                    .setParallelism(flinkEnv.getSourceParallelism());


        OutputTag<SimpleSignalDelta> lateEventOutputTag = new OutputTag<>("late-simple-signal-delta", TypeInformation.of(SimpleSignalDelta.class));


        SingleOutputStreamOperator<CJSMetric> windowStream =
                sourceStream.keyBy(new SimpleSignalDeltaKeySelector())
                            .window(TumblingEventTimeWindows.of(Time.minutes(WINDOW_MINS)))
                            .sideOutputLateData(lateEventOutputTag)
                            .aggregate(new CJSMetricAggFunction(), new CJSMetricProcessWindowFunction())
                            .name(windowOpName)
                            .uid(windowOpUid)
                            .setParallelism(flinkEnv.getSinkParallelism());

        windowStream.sinkTo(new PrintSink<>())
                    .name("Print Sink")
                    .uid("sink-print")
                    .setParallelism(flinkEnv.getSinkParallelism());

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


        windowStream.addSink(new SherlockEventSinkFunctionCJSMetric(
                            flinkEnv.getString(SHERLOCK_ENDPOINT),
                            flinkEnv.getString(SHERLOCK_APPLICATION_ID),
                            flinkEnv.getString(SHERLOCK_NAMESPACE),
                            flinkEnv.getString(SHERLOCK_SCHEMA),
                            100
                            ))
                    .name(sherlockSinkOpName)
                    .uid(sherlockSinkOpUid)
                    .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));
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
