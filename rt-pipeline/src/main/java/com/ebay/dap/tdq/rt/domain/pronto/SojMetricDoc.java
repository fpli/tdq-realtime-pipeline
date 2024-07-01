package com.ebay.dap.tdq.rt.domain.pronto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SojMetricDoc {

    @JsonProperty("page_id")
    private int pageId;

    @JsonProperty("site_id")
    private int siteId;

    @JsonProperty("app_id")
    private int appId;

    @JsonProperty("bot")
    private int bot;

    @JsonProperty("event_cnt")
    private long eventCount;

//    @JsonProperty("acc_rt_event_cnt")
//    private long accEventCount;
//
//    @JsonProperty("batch_event_cnt")
//    private long batchEventCount;

    @JsonProperty("metric_time")
    private long metricTime;

    @JsonProperty("metric_time_iso_str")
    private String metricTimeISOStr;

    @JsonProperty("upstream_process_time")
    private long upstreamProcessTime;

}
