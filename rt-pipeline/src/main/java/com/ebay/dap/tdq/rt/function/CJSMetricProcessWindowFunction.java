package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import com.ebay.dap.tdq.rt.domain.CJSMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;

@Slf4j
public class CJSMetricProcessWindowFunction extends ProcessWindowFunction<CJSMetric, CJSMetric, Tuple3<String, String, String>, TimeWindow> {

    @Override
    public void process(Tuple3<String, String, String> key, ProcessWindowFunction<CJSMetric, CJSMetric, Tuple3<String, String, String>, TimeWindow>.Context context, Iterable<CJSMetric> elements, Collector<CJSMetric> out) throws Exception {
        log.info("key is {}", key.toString());

        long windowStart = context.window().getStart();

        LocalDateTime eventTime = DateTimeUtils.tsToLocalDateTime(windowStart);

        CJSMetric cjsMetric = elements.iterator().next();
        // use window start time as metric time
        cjsMetric.setMetricTime(windowStart);
        cjsMetric.setMetricTimeStr(eventTime.toString());

        out.collect(cjsMetric);
    }
}
