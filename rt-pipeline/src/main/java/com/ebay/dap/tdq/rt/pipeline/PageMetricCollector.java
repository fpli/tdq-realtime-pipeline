package com.ebay.dap.tdq.rt.pipeline;

import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.connector.hdfs.ParquetAvroWritersWithCompression;
import com.ebay.dap.tdq.flink.connector.pronto.ProntoEnv;
import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import com.ebay.dap.tdq.rt.function.MetricAggFunction;
import com.ebay.dap.tdq.rt.function.MetricProcessWindowFunction;
import com.ebay.dap.tdq.rt.function.PageMetricProntoSinkFunction;
import com.ebay.dap.tdq.rt.function.SimpleSojEventDeserializationSchema;
import com.ebay.dap.tdq.rt.sink.LateSimpleSojEventBucketAssigner;
import com.ebay.dap.tdq.rt.watermark.SimpleSojEventTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkBase;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.util.OutputTag;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_PARALLELISM_SOURCE;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_SINK_HDFS_PATH;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN;
import static com.ebay.dap.tdq.common.constant.Property.RHEOS_REGISTRY_URL;

public class PageMetricCollector {

    public static void main(String[] args) throws Exception {

        final String sourceOpName = "Kafka - behavior.totalv3";
        final String sourceOpUid = "kafka_source";
        final String windowOpName = "Tumbling Window - Hourly Page Metric Agg";
        final String windowOpUid = "metric_agg";
        final String sinkOpName = "Pronto";
        final String sinkOpUid = "pronto_sink";
        final String lateSinkOpName = "Hercules";
        final String lateSinkOpUid = "hdfs_sink";

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
                                                                          .window(TumblingEventTimeWindows.of(Time.hours(1)))
                                                                          .sideOutputLateData(lateEventOutputTag)
                                                                          .aggregate(new MetricAggFunction(), new MetricProcessWindowFunction())
                                                                          .name(windowOpName)
                                                                          .uid(windowOpUid)
                                                                          .setParallelism(flinkEnv.getInteger(FLINK_APP_PARALLELISM_SOURCE));

        ProntoEnv prontoEnv = flinkEnv.getProntoEnv();
        List<HttpHost> httpHosts = Collections.singletonList(new HttpHost(
                prontoEnv.getHost(), prontoEnv.getPort(), prontoEnv.getScheme()
        ));

        // use an ElasticsearchSink.Builder to create an ElasticsearchSink
        ElasticsearchSink.Builder<PageMetric> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new PageMetricProntoSinkFunction("my-index")
        );

        esSinkBuilder.setRestClientFactory(restClientBuilder -> {
            restClientBuilder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Content-Type", "application/json")});
            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(prontoEnv.getUsername(), prontoEnv.getPassword()));
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        });

        // configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered
        esSinkBuilder.setBulkFlushMaxActions(1);
        esSinkBuilder.setBulkFlushBackoff(true);
        esSinkBuilder.setBulkFlushBackoffRetries(3);
        esSinkBuilder.setBulkFlushBackoffType(ElasticsearchSinkBase.FlushBackoffType.CONSTANT);
        esSinkBuilder.setBulkFlushBackoffDelay(3000);

        windowStream.addSink(esSinkBuilder.build())
                    .name(sinkOpName)
                    .uid(sinkOpUid)
                    .setParallelism(1);

        StreamingFileSink<SimpleSojEvent> hdfsSink = StreamingFileSink
                .forBulkFormat(new Path(flinkEnv.getString(FLINK_APP_SINK_HDFS_PATH)),
                        ParquetAvroWritersWithCompression.forReflectRecord(SimpleSojEvent.class))
                .withBucketAssigner(new LateSimpleSojEventBucketAssigner())
                .build();

        windowStream.getSideOutput(lateEventOutputTag)
                    .addSink(hdfsSink)
                    .name(lateSinkOpName)
                    .uid(lateSinkOpUid)
                    .setParallelism(50);

        // submit flink job
        flinkEnv.execute(executionEnvironment);

    }

}
