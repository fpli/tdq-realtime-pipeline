package com.ebay.dap.tdq.flink.connector.kafka;

import com.ebay.dap.tdq.common.avro.RheosHeader;
import com.ebay.dap.tdq.common.avro.SojEvent;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AvroKafkaSerializerTest {


    AvroKafkaSerializer<SojEvent> sojEventAvroKafkaSerializer;

    SojEvent sojEvent;

    @BeforeEach
    void init() {
        sojEventAvroKafkaSerializer = new AvroKafkaSerializer<>(SojEvent.class, "guid");
        sojEvent = new SojEvent();
        sojEvent.setRheosHeader(new RheosHeader(0L,0L,0,"eventId","producerId"));
        sojEvent.setGuid("abc");
        sojEvent.setPageId(123);
        sojEvent.setClientData(new HashMap<>());
        sojEvent.setApplicationPayload(new HashMap<>());
    }

    @Test
    void encodeKey_singleField_OK() {
        byte[] actual = sojEventAvroKafkaSerializer.encodeKey(sojEvent);
        log.info(Arrays.toString(actual));
        byte[] expected = "abc".getBytes(StandardCharsets.UTF_8);
        log.info(Arrays.toString(expected));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void encodeKey_multiFields_OK() {
        sojEventAvroKafkaSerializer = new AvroKafkaSerializer<>(SojEvent.class, Lists.newArrayList("guid", "pageId"));
        byte[] actual = sojEventAvroKafkaSerializer.encodeKey(sojEvent);
        log.info(Arrays.toString(actual));
        byte[] expected = "abc,123".getBytes(StandardCharsets.UTF_8);
        log.info(Arrays.toString(expected));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void encodeKey_multiFieldsWithNull_OK() {
        sojEventAvroKafkaSerializer = new AvroKafkaSerializer<>(SojEvent.class, Lists.newArrayList("guid", "pageId"));
        sojEvent.setPageId(null);
        byte[] actual = sojEventAvroKafkaSerializer.encodeKey(sojEvent);
        log.info(Arrays.toString(actual));
        byte[] expected = "abc,null".getBytes(StandardCharsets.UTF_8);
        log.info(Arrays.toString(expected));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void encodeKey_fieldNotExist_throwException() throws Exception {
        sojEventAvroKafkaSerializer = new AvroKafkaSerializer<>(SojEvent.class, "xxx");

        Exception exception = Assertions.assertThrows(SerializationException.class, () -> {
            sojEventAvroKafkaSerializer.encodeKey(sojEvent);
        });

        assertThat(exception).hasCauseInstanceOf(NoSuchFieldException.class);
        assertThat(exception).hasMessage("java.lang.NoSuchFieldException: xxx");
    }

    @Test
    void encodeValue_OK() throws Exception {
        byte[] bytes = sojEventAvroKafkaSerializer.encodeValue(sojEvent);
        assertThat(bytes).isNotNull();

        DatumReader<SojEvent> reader = new SpecificDatumReader<>(SojEvent.getClassSchema());
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);

        SojEvent actual = reader.read(null, decoder);

        assertThat(actual.getGuid()).isEqualTo("abc");

    }

    @Test
    void encodeValue_throwException() throws Exception {
        sojEvent.setRheosHeader(null);

        Exception exception = Assertions.assertThrows(SerializationException.class, () -> {
            sojEventAvroKafkaSerializer.encodeValue(sojEvent);
        });

        assertThat(exception).hasCauseInstanceOf(NullPointerException.class);
    }
}