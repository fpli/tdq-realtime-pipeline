package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MetricProcessWindowFunction extends ProcessWindowFunction<PageMetric, PageMetric, Integer, TimeWindow> {

    @Override
    public void process(Integer integer, ProcessWindowFunction<PageMetric, PageMetric, Integer, TimeWindow>.Context context, Iterable<PageMetric> elements, Collector<PageMetric> out) throws Exception {

        LocalDateTime eventTime = Instant.ofEpochMilli(context.window().getStart())
                                         .atZone(ZoneId.of("GMT-7"))
                                         .toLocalDateTime();

        PageMetric next = elements.iterator().next();
        next.setDt(eventTime.format(DateTimeFormatter.ISO_DATE));
        next.setHr(String.valueOf(eventTime.getHour()));
        out.collect(next);
    }
}
