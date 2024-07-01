package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.SojMetric;
import com.ebay.dap.tdq.rt.domain.pronto.SojMetricDoc;
import org.apache.flink.api.common.functions.MapFunction;

public class SojMetricDocMapFunction implements MapFunction<SojMetric, SojMetricDoc> {

    @Override
    public SojMetricDoc map(SojMetric sojMetric) throws Exception {
        SojMetricDoc sojMetricDoc = new SojMetricDoc();

        sojMetricDoc.setPageId(sojMetric.getPageId());
        sojMetricDoc.setSiteId(sojMetric.getSiteId());
        sojMetricDoc.setAppId(sojMetric.getAppId());
        sojMetricDoc.setBot(sojMetric.getBot());
        sojMetricDoc.setEventCount(sojMetric.getCount());

        sojMetricDoc.setMetricTime(sojMetric.getMetricTime());
        sojMetricDoc.setMetricTimeISOStr(sojMetric.getMetricTimeStr());

        return sojMetricDoc;
    }
}
