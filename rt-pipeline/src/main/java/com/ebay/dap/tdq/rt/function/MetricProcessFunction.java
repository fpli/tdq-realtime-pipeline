package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

@Slf4j
public class MetricProcessFunction extends ProcessFunction<PageMetric, PageMetric> {

    @Override
    public void processElement(PageMetric value, ProcessFunction<PageMetric, PageMetric>.Context ctx, Collector<PageMetric> out) throws Exception {
        log.info(value.toString());
        log.info(ctx.timestamp().toString());
    }
}
