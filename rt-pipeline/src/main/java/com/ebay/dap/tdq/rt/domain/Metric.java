package com.ebay.dap.tdq.rt.domain;

import lombok.Data;

@Data
public abstract class Metric {

    protected String metricValueStr;

    protected Long metricTime;

    protected String metricTimeStr;

}
