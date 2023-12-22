package com.ebay.dap.tdq.flink.connector.kafka.schema;

import com.ebay.dap.tdq.common.avro.RheosHeader;
import com.ebay.dap.tdq.flink.connector.kafka.AvroKafkaDeserializer;
import com.ebay.dap.tdq.flink.connector.kafka.KafkaDeserializer;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class RheosHeaderDeserializationSchema implements KafkaRecordDeserializationSchema<RheosHeader> {

    private transient KafkaDeserializer<RheosHeader> kafkaDeserializer;

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);

        this.kafkaDeserializer = new AvroKafkaDeserializer<>(RheosHeader.class);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<RheosHeader> out) throws IOException {
        RheosHeader event = kafkaDeserializer.decodeValue(record.value());

        out.collect(event);
    }

    @Override
    public TypeInformation<RheosHeader> getProducedType() {
        return TypeInformation.of(RheosHeader.class);
    }

}
