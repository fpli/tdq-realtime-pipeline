package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.ebay.dap.tdq.rt.domain.SojMetric;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Slf4j
public class SojMetricProcessWindowFunction extends ProcessWindowFunction<SojMetric, SojMetric, Tuple3<Integer, Integer, Integer>, TimeWindow> {


//    private final Set<Integer> top50PageIds = Sets.newHashSet(
//            2047675,2047937,2048309,2052300,2058891,2062857,2065432,2349624,2351460,2353552,2355844,2367289,
//            2376473,2380424,2380676,2380866,2481888,2487283,2489527,2492446,2492448,2492450,2493971,2506613,2510300,
//            2512348,2524149,2543286,2543464,2544535,2553215,2565282,2565297,2566055,3196657,3216531,3243862,3289402,
//            3418065,3658866,3748097,3820580,3851689,3853885,4256617,4268716,4375194,4380942,4429486,4451299
//    );


    @Override
    public void process(Tuple3<Integer, Integer, Integer> key, ProcessWindowFunction<SojMetric, SojMetric, Tuple3<Integer, Integer, Integer>, TimeWindow>.Context context, Iterable<SojMetric> elements, Collector<SojMetric> out) throws Exception {
        long windowStart = context.window().getStart();
        LocalDateTime windowStartDt = DateTimeUtils.epochMilliToLocalDateTime(windowStart);

        SojMetric sojMetric = elements.iterator().next();

        // set metric time
        sojMetric.setMetricTime(windowStart);
        sojMetric.setMetricTimeStr(windowStartDt.toString());

        sojMetric.setPageId(key.f0);
        sojMetric.setSiteId(key.f1);
        sojMetric.setAppId(key.f2);

        // log top50 pageId record
//        if (top50PageIds.contains(next.getPageId())) {
//            log.info("Processed window [{} - {}] for pageId {}, event count is {}",
//                    next.getDt(), next.getHr(), next.getPageId(), next.getEventCount());
//        }

        out.collect(sojMetric);
    }
}
