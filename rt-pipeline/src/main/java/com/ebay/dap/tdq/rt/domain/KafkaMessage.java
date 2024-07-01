package com.ebay.dap.tdq.rt.domain;

import lombok.Data;

@Data
public abstract class KafkaMessage {

    // fields for debugging
    protected int kafkaPartition;

    protected long kafkaOffset;

    protected long kafkaTimestamp;

}
