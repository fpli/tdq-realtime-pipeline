package com.ebay.dap.tdq.flink.connector.kafka.schema;

import com.ebay.dap.tdq.flink.connector.kafka.AvroKafkaDeserializer;
import com.ebay.dap.tdq.flink.connector.kafka.KafkaDeserializer;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class AvroKafkaDeserializationSchema<T extends SpecificRecord> implements KafkaDeserializationSchema<T> {

    private final Class<T> clazz;
    private transient KafkaDeserializer<T> kafkaDeserializer;

    public AvroKafkaDeserializationSchema(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaDeserializationSchema.super.open(context);
        kafkaDeserializer = new AvroKafkaDeserializer<>(clazz);
    }

    @Override
    public boolean isEndOfStream(T nextElement) {
        return false;
    }

    @Override
    public T deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
        return kafkaDeserializer.decodeValue(record.value());
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(clazz);
    }
}
