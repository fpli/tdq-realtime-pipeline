package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SojPageMetric extends CountMetric {

    private Integer pageId;

}
