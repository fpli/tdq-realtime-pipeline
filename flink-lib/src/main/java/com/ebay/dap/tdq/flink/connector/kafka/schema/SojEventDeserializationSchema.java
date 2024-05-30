package com.ebay.dap.tdq.flink.connector.kafka.schema;

import com.ebay.dap.tdq.common.model.avro.SojEvent;
import com.ebay.dap.tdq.flink.connector.kafka.AvroKafkaDeserializer;
import com.ebay.dap.tdq.flink.connector.kafka.KafkaDeserializer;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class SojEventDeserializationSchema implements KafkaRecordDeserializationSchema<SojEvent> {

    private transient KafkaDeserializer<SojEvent> kafkaDeserializer;

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);

        this.kafkaDeserializer = new AvroKafkaDeserializer<>(SojEvent.class);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<SojEvent> out) throws IOException {
        SojEvent sojEvent = kafkaDeserializer.decodeValue(record.value());

        out.collect(sojEvent);
    }

    @Override
    public TypeInformation<SojEvent> getProducedType() {
        return TypeInformation.of(SojEvent.class);
    }

}
