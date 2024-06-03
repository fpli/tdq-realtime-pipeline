package com.ebay.dap.tdq.rt.function.impl;

import com.ebay.dap.tdq.rt.domain.CJSMetric;
import com.ebay.dap.tdq.rt.function.SherlockEventSinkFunctionBase;
import io.opentelemetry.api.common.AttributesBuilder;

import java.util.function.BiConsumer;

public class SherlockEventSinkFunctionCJSMetric extends SherlockEventSinkFunctionBase<CJSMetric> {

    public SherlockEventSinkFunctionCJSMetric(String endpoint, String applicationId, String namespace, String schema, int maxBatchSize) {
        super(endpoint, applicationId, namespace, schema, maxBatchSize);
    }

    @Override
    public BiConsumer<CJSMetric, AttributesBuilder> getBiConsumer(){
        return  (cjsmetric, attributesBuilder) -> {
            attributesBuilder.put("metricValueStr", cjsmetric.getMetricValueStr());
            attributesBuilder.put("metricTime", cjsmetric.getMetricTime());
            attributesBuilder.put("metricTimeStr", cjsmetric.getMetricTimeStr());
            attributesBuilder.put("count", cjsmetric.getCount());
            attributesBuilder.put("name", cjsmetric.getName());
            attributesBuilder.put("eventSource", cjsmetric.getEventSource());
            attributesBuilder.put("eventType", cjsmetric.getEventType());
            attributesBuilder.put("eventAction", cjsmetric.getEventAction());
        };
    }
}
