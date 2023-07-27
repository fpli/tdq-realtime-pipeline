package com.ebay.dap.tdq.flink.connector.kafka;

public interface KafkaDeserializer<T> {

    String decodeKey(byte[] data);

    T decodeValue(byte[] data);
}
