package com.ebay.dap.tdq.flink.connector.kafka;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.nio.charset.StandardCharsets;

@Slf4j
public class AvroKafkaDeserializer<T extends SpecificRecord> implements KafkaDeserializer<T> {

    private final Class<T> clazz;
    private final DatumReader<T> reader;

    public AvroKafkaDeserializer(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);

        this.clazz = clazz;
        this.reader = new SpecificDatumReader<>(clazz);
    }

    @Override
    public String decodeKey(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public T decodeValue(byte[] data) {
        try {
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            return reader.read(null, decoder);
        } catch (Exception e) {
            log.error("Cannot decode kafka message", e);
            throw new SerializationException("Error when Avro deserializing for class: " + clazz.getName(), e);
        }
    }
}
