package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CJSMetric extends CountMetric {

    private String name;

    private String eventSource;

    private String eventType;

    private String eventAction;

}
