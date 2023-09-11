package com.ebay.dap.tdq.rt.domain;

import lombok.Data;

@Data
public class PageMetric {

    private Integer pageId;

    private Long eventCount;

    private String dt;

    private String hr;

    public PageMetric(Long eventCount) {
        this.eventCount = eventCount;
    }
}
