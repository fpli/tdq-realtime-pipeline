package com.ebay.dap.tdq.rt.domain;


import lombok.Data;

@Data
public class SimpleSignalDelta {

    private String name;

    private String eventSource;

    private String eventType;

    private String eventAction;

    private Long timestamp;

}
