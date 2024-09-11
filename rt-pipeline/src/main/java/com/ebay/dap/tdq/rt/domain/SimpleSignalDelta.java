package com.ebay.dap.tdq.rt.domain;


import lombok.Data;

@Data
public class SimpleSignalDelta extends KafkaMessage {

    private String signalName;

    private Integer pageId;

    private Integer siteId;

    private String eventSource;

    private String eventPrimaryId;

    private String unifiedEventId;

    private Long timestamp;

    private Long processTime;

}
