package com.ebay.dap.tdq.flink.connector.kafka;

import java.util.List;

public interface KafkaSerializer<T> {

    byte[] encodeKey(T data);

    byte[] encodeValue(T data);

}
