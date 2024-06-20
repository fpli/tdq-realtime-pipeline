package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CJSMetric extends CountMetric {

    private String signalName;

    private String eventSource;

    private Integer pageId;

    private Integer siteId;

}
