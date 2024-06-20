package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@NoArgsConstructor
public class PageMetric {

    private Integer pageId;

    private Long eventCount;

    private String dt;

    private Integer hr;

    private Long metricTime;

    public PageMetric(Long eventCount) {
        this.eventCount = eventCount;
    }
}
