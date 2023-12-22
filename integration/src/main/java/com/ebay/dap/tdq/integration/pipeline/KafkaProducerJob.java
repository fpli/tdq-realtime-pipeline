package com.ebay.dap.tdq.integration.pipeline;

import com.ebay.dap.tdq.common.avro.RheosHeader;
import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.connector.kafka.schema.AvroKafkaSerializationSchema;
import com.ebay.dap.tdq.integration.function.LocalSourceFunction;
import com.ebay.dap.tdq.integration.function.SimpleLogFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_SINK_KAFKA_DEFAULT_TOPIC;

public class KafkaProducerJob {

    public static void main(String[] args) throws Exception {

        FlinkEnv flinkEnv = new FlinkEnv(args);

        StreamExecutionEnvironment executionEnvironment = flinkEnv.local();

        DataStream<RheosHeader> sourceDataStream = executionEnvironment.addSource(new LocalSourceFunction())
                                                                       .name("Local Source")
                                                                       .uid("local-source")
                                                                       .setParallelism(1)
                                                                       .disableChaining();


        DataStream<RheosHeader> dataStream = sourceDataStream.map(new SimpleLogFunction<>())
                                                             .name("Log")
                                                             .uid("log")
                                                             .setParallelism(1);

        FlinkKafkaProducer<RheosHeader> flinkKafkaProducer = new FlinkKafkaProducer<>(
                flinkEnv.getString(FLINK_APP_SINK_KAFKA_DEFAULT_TOPIC),
                new AvroKafkaSerializationSchema<>(RheosHeader.class, flinkEnv.getString(FLINK_APP_SINK_KAFKA_DEFAULT_TOPIC), "eventId"),
                flinkEnv.getKafkaProducerProps(),
                FlinkKafkaProducer.Semantic.AT_LEAST_ONCE
        );


        dataStream.addSink(flinkKafkaProducer)
                  .name("Kafka Sink")
                  .uid("kafka-sink")
                  .setParallelism(1);

        // submit flink job
        flinkEnv.execute(executionEnvironment);
    }

}
