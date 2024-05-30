package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.CJSMetric;
import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import org.apache.flink.api.common.functions.AggregateFunction;

public class CJSMetricAggFunction implements AggregateFunction<SimpleSignalDelta, CJSMetric, CJSMetric> {

    @Override
    public CJSMetric createAccumulator() {
        CJSMetric cjsMetric = new CJSMetric();
        cjsMetric.setCount(0L);
        return cjsMetric;
    }

    @Override
    public CJSMetric add(SimpleSignalDelta value, CJSMetric accumulator) {
        accumulator.setCount(accumulator.getCount() + 1);
        return accumulator;
    }

    @Override
    public CJSMetric getResult(CJSMetric accumulator) {
        accumulator.setMetricValueStr(accumulator.getCount().toString());
        return accumulator;
    }

    @Override
    public CJSMetric merge(CJSMetric a, CJSMetric b) {
        return null;
    }
}
