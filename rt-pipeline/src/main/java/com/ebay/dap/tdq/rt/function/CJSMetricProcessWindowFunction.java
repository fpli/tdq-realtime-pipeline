package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import com.ebay.dap.tdq.rt.domain.CJSMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;

@Slf4j
public class CJSMetricProcessWindowFunction extends ProcessWindowFunction<CJSMetric, CJSMetric, Tuple4<String, String, Integer, Integer>, TimeWindow> {

    @Override
    public void process(Tuple4<String, String, Integer, Integer> key, ProcessWindowFunction<CJSMetric, CJSMetric, Tuple4<String, String, Integer, Integer>, TimeWindow>.Context context, Iterable<CJSMetric> elements, Collector<CJSMetric> out) throws Exception {

        long windowStart = context.window().getStart();

        LocalDateTime windowStartDt = DateTimeUtils.epochMilliToLocalDateTime(windowStart);

        CJSMetric cjsMetric = elements.iterator().next();
        cjsMetric.setSignalName(key.f0);
        cjsMetric.setEventSource(key.f1);
        cjsMetric.setPageId(key.f2);
        cjsMetric.setSiteId(key.f3);

        // use window start time as metric time
        cjsMetric.setMetricTime(windowStart);
        cjsMetric.setMetricTimeStr(windowStartDt.toString());

        out.collect(cjsMetric);
    }
}
