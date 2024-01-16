package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.common.functions.MapFunction;

public class LateEventToMetricsMapFunction implements MapFunction<SimpleSojEvent, PageMetric> {

    private int window;
    private boolean calFlag;

    /**
     *
     * @param window_mins window size in minutes, used to calculate event's window start time
     * @param calFlag   true: calculate event's window start time, false: use original event time
     */
    public LateEventToMetricsMapFunction(int window_mins, boolean calFlag) {
        this.window = window_mins * 60 * 1000;
        this.calFlag = calFlag;
    }

    @Override
    public PageMetric map(SimpleSojEvent value) throws Exception {
        PageMetric pageMetric = new PageMetric();
        pageMetric.setPageId(value.getPageId());
        if (calFlag) {
            // calculate event's window start time
            pageMetric.setMetricTime(value.getEventTimestamp() - (value.getEventTimestamp() % window));
        } else {
            // use original event time
            pageMetric.setMetricTime(value.getEventTimestamp());
        }
        pageMetric.setEventCount(1L);
        return pageMetric;
    }

}
