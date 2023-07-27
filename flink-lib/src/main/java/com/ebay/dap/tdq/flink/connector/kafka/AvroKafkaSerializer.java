package com.ebay.dap.tdq.flink.connector.kafka;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.common.errors.SerializationException;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class AvroKafkaSerializer<T extends SpecificRecord> implements KafkaSerializer<T> {

    private final Class<T> clazz;

    private final List<String> fields;

    private final DatumWriter<T> writer;

    public AvroKafkaSerializer(Class<T> clazz, String field) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(field);
        this.clazz = clazz;
        this.fields = Lists.newArrayList(field);
        this.writer = new SpecificDatumWriter<>(clazz);
    }

    public AvroKafkaSerializer(Class<T> clazz, List<String> fields) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(fields);
        this.clazz = clazz;
        this.fields = Lists.newArrayList(fields);
        this.writer = new SpecificDatumWriter<>(clazz);
    }

    @Override
    public byte[] encodeKey(T data) {
        if (data == null || CollectionUtils.isEmpty(fields)) {
            return null;
        } else {
            Field field = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fields.size(); i++) {
                try {
                    field = data.getClass().getDeclaredField(fields.get(i));
                    field.setAccessible(true);
                    Object value = field.get(data);
                    sb.append(value);
                    if (i < fields.size() - 1) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    log.error("Cannot encode key for field {}", fields.get(i));
                    throw new SerializationException(e);
                }
            }
            return sb.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public byte[] encodeValue(T data) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] serializedValue = null;
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(data, encoder);
            encoder.flush();
            serializedValue = out.toByteArray();
            out.close();
            return serializedValue;
        } catch (Exception e) {
            throw new SerializationException("Error when Avro serializing for class: " + clazz.getName(), e);
        }
    }
}
