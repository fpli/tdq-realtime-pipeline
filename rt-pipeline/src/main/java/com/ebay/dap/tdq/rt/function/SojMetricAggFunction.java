package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import com.ebay.dap.tdq.rt.domain.SojMetric;
import org.apache.flink.api.common.functions.AggregateFunction;

public class SojMetricAggFunction implements AggregateFunction<SimpleSojEvent, SojMetric, SojMetric> {

    @Override
    public SojMetric createAccumulator() {
        SojMetric sojMetric = new SojMetric();
        sojMetric.setCount(0L);
        return sojMetric;
    }

    @Override
    public SojMetric add(SimpleSojEvent value, SojMetric accumulator) {
        accumulator.setPageId(value.getPageId());
        accumulator.setCount(accumulator.getCount() + 1);
        return accumulator;
    }

    @Override
    public SojMetric getResult(SojMetric accumulator) {
        accumulator.setMetricValueStr(accumulator.getCount().toString());
        return accumulator;
    }

    @Override
    public SojMetric merge(SojMetric a, SojMetric b) {
        return null;
    }
}
