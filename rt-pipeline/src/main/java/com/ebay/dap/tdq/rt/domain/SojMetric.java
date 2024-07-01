package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SojMetric extends CountMetric {

    private Integer pageId;

    private Integer siteId;

    private Integer appId;

    private Integer bot;

}
