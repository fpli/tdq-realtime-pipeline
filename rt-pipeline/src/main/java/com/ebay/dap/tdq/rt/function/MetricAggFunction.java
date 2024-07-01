package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.common.functions.AggregateFunction;

@Deprecated
public class MetricAggFunction implements AggregateFunction<SimpleSojEvent, PageMetric, PageMetric> {

    @Override
    public PageMetric createAccumulator() {
        return new PageMetric(0L);
    }

    @Override
    public PageMetric add(SimpleSojEvent value, PageMetric accumulator) {
        accumulator.setPageId(value.getPageId());
        accumulator.setEventCount(accumulator.getEventCount() + 1);
        return accumulator;
    }

    @Override
    public PageMetric getResult(PageMetric accumulator) {
        return accumulator;
    }

    @Override
    public PageMetric merge(PageMetric a, PageMetric b) {
        return null;
    }
}
