package com.ebay.dap.tdq.rt.domain;


import lombok.Data;

@Data
public class SimpleSignalDelta {

    private String signalName;

    private Integer pageId;

    private Integer siteId;

    private String eventSource;

    private String eventPrimaryId;

    private String unifiedEventId;

    private Long timestamp;

    private Long processTime;

    // fields for debugging
    private int kafkaPartition;

    private long kafkaOffset;

    private long kafkaTimestamp;

}
