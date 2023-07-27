package com.ebay.dap.tdq.flink.connector.kafka.schema;

import com.ebay.dap.tdq.flink.connector.kafka.AvroKafkaSerializer;
import com.ebay.dap.tdq.flink.connector.kafka.KafkaSerializer;
import com.google.common.base.Preconditions;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class AvroKafkaSerializationSchema<T extends SpecificRecord> implements KafkaSerializationSchema<T> {
    private final Class<T> clazz;
    private final String topic;
    private final List<String> keyFields;
    private transient KafkaSerializer<T> kafkaSerializer;

    public AvroKafkaSerializationSchema(Class<T> clazz, String topic, List<String> keyFields) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(topic);
        Preconditions.checkNotNull(keyFields);

        this.clazz = clazz;
        this.topic = topic;
        this.keyFields = keyFields;
    }

    public AvroKafkaSerializationSchema(Class<T> clazz, String topic, String keyField) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(topic);
        Preconditions.checkNotNull(keyField);

        this.clazz = clazz;
        this.topic = topic;
        this.keyFields = Collections.singletonList(keyField);
    }

    @Override
    public void open(SerializationSchema.InitializationContext context) throws Exception {
        KafkaSerializationSchema.super.open(context);
        this.kafkaSerializer = new AvroKafkaSerializer<>(clazz, keyFields);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(T element, @Nullable Long timestamp) {
        return new ProducerRecord<>(topic,
                kafkaSerializer.encodeKey(element),
                kafkaSerializer.encodeValue(element));
    }
}
