package com.ebay.dap.tdq.integration.pipeline;

import com.ebay.dap.tdq.common.avro.RheosHeader;
import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.connector.kafka.schema.RheosHeaderDeserializationSchema;
import com.ebay.dap.tdq.integration.function.SimpleLogFunction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.DiscardingSink;

public class KafkaConsumerJob {

    public static void main(String[] args) throws Exception {

        FlinkEnv flinkEnv = new FlinkEnv(args);

//        Integer webUIPort = flinkEnv.getInteger("flink.web-ui.port");

        StreamExecutionEnvironment executionEnvironment = flinkEnv.init();

        KafkaSource<RheosHeader> kafkaSource = KafkaSource.<RheosHeader>builder()
                                                          .setBootstrapServers(flinkEnv.getSourceKafkaBrokers())
                                                          .setGroupId(flinkEnv.getSourceKafkaGroupId())
                                                          .setTopics(flinkEnv.getSourceKafkaTopics())
                                                          .setProperties(flinkEnv.getKafkaConsumerProps())
                                                          .setStartingOffsets(flinkEnv.getSourceKafkaStartingOffsets())
                                                          .setDeserializer(new RheosHeaderDeserializationSchema())
                                                          .build();

        DataStream<RheosHeader> sourceDataStream = executionEnvironment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")
                                                                       .uid("kafka-source")
                                                                       .setParallelism(1)
                                                                       .disableChaining();


        DataStream<RheosHeader> dataStream = sourceDataStream.map(new SimpleLogFunction<>())
                                                             .name("Log")
                                                             .uid("log")
                                                             .setParallelism(1);

        dataStream.addSink(new DiscardingSink<>())
                  .name("Sink")
                  .uid("sink")
                  .setParallelism(1);

        // submit flink job
        flinkEnv.execute(executionEnvironment);
    }

}
