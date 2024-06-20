package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class CountMetric extends Metric {

    protected Long count;
}
