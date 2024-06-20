package com.ebay.dap.tdq.rt.sink;

import com.ebay.dap.tdq.rt.domain.CJSMetric;
import io.opentelemetry.api.common.AttributesBuilder;

import java.util.function.BiConsumer;

public class SherlockEventSinkFunctionCJSMetric extends SherlockEventSinkFunctionBase<CJSMetric> {

    public SherlockEventSinkFunctionCJSMetric(String endpoint, String applicationId, String namespace, String schema, int maxBatchSize) {
        super(endpoint, applicationId, namespace, schema, maxBatchSize);
    }

    @Override
    public BiConsumer<CJSMetric, AttributesBuilder> getBiConsumer(){
        return  (cjsmetric, attrBuilder) -> {
            attrBuilder.put("metric_value_str", cjsmetric.getMetricValueStr());
            attrBuilder.put("metric_time", cjsmetric.getMetricTime());
            attrBuilder.put("metric_time_str", cjsmetric.getMetricTimeStr());
            attrBuilder.put("count", cjsmetric.getCount());
            attrBuilder.put("signal_name", cjsmetric.getSignalName());
            attrBuilder.put("event_source", cjsmetric.getEventSource());
            attrBuilder.put("page_id", cjsmetric.getPageId());
            attrBuilder.put("site_id", cjsmetric.getSiteId());
        };
    }
}
