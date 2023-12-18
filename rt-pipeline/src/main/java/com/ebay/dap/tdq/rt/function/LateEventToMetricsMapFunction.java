package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.common.functions.MapFunction;

public class LateEventToMetricsMapFunction implements MapFunction<SimpleSojEvent, PageMetric> {

    private int window;

    public LateEventToMetricsMapFunction(int window_mins) {
        this.window = window_mins * 60 * 1000;
    }

    @Override
    public PageMetric map(SimpleSojEvent value) throws Exception {
        PageMetric pageMetric = new PageMetric();
        pageMetric.setPageId(value.getPageId());
        // calculate event's window start time
        pageMetric.setMetricTime(value.getEventTimestamp() - (value.getEventTimestamp() % window));
        pageMetric.setEventCount(1L);
        return pageMetric;
    }

}
