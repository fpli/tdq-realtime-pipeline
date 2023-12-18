package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.logs.LogRecordBuilder;
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter;
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporterBuilder;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessorBuilder;
import io.opentelemetry.sdk.resources.Resource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.time.Instant;

public class SherlockEventSinkFunction extends RichSinkFunction<PageMetric> {

    private String endpoint;
    private String applicationId;
    private String namespace;
    private String schema;
    private String name;

    public SherlockEventSinkFunction(String endpoint, String applicationId, String namespace, String schema, String name) {
        this.endpoint = endpoint;
        this.applicationId = applicationId;
        this.namespace = namespace;
        this.schema = schema;
        this.name = name;
    }

    private LogRecordProcessor logRecordProcessor;
    private SdkLoggerProvider provider;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        OtlpHttpLogRecordExporterBuilder builder = OtlpHttpLogRecordExporter.builder()
                .setEndpoint(endpoint)
                .setCompression("gzip")
                .addHeader("Authorization", "Bearer " + applicationId);
        OtlpHttpLogRecordExporter exporter = builder.build();
        BatchLogRecordProcessorBuilder batchLogProcessorBuilder = BatchLogRecordProcessor.builder(exporter);
        batchLogProcessorBuilder.setMaxExportBatchSize(100);
        logRecordProcessor = batchLogProcessorBuilder.build();
        SdkLoggerProviderBuilder sdkLoggerProviderBuilder = SdkLoggerProvider.builder();
        sdkLoggerProviderBuilder.setResource(Resource.create(Attributes.builder()
                .put("_namespace_", namespace)
                .put("_schema_", schema)
                .put("__name__", name)
                .build()));
        provider = sdkLoggerProviderBuilder.addLogRecordProcessor(logRecordProcessor).build();
    }

    @Override
    public void invoke(PageMetric value, Context context) throws Exception {
        super.invoke(value, context);
        LogRecordBuilder logRecordBuilder = provider.loggerBuilder("").build().logRecordBuilder();
        logRecordBuilder.setEpoch(Instant.ofEpochMilli(value.getMetricTime()));
        logRecordBuilder.setBody("");
        AttributesBuilder attributesBuilder = Attributes.builder();
        // TODO change attribute name here, it should be a subset of the schema.
        // Is this field required as we have set in `setEpoch` method
        attributesBuilder.put("metricTime", value.getMetricTime());
        attributesBuilder.put("pageId", value.getPageId());
        attributesBuilder.put("eventCount", value.getEventCount());
        logRecordBuilder.setAllAttributes(attributesBuilder.build());
        logRecordBuilder.emit();
    }

    @Override
    public void close() throws Exception {
        super.close();
        logRecordProcessor.close();
        provider.close();
    }

}
