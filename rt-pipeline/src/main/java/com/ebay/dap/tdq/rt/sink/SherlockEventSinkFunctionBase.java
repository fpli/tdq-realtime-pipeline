package com.ebay.dap.tdq.rt.sink;

import com.ebay.dap.tdq.rt.domain.Metric;
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
import java.util.function.BiConsumer;

public abstract class SherlockEventSinkFunctionBase<T extends Metric> extends RichSinkFunction<T> {
    private String endpoint;
    private String applicationId;
    private String namespace;
    private String schema;

    private int maxBatchSize;

    private LogRecordProcessor logRecordProcessor;
    private SdkLoggerProvider provider;

    private BiConsumer<T, AttributesBuilder> biConsumer;

    public SherlockEventSinkFunctionBase(String endpoint, String applicationId, String namespace, String schema, int maxBatchSize) {
        this.endpoint = endpoint;
        this.applicationId = applicationId;
        this.namespace = namespace;
        this.schema = schema;
        this.maxBatchSize = maxBatchSize;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        OtlpHttpLogRecordExporterBuilder builder = OtlpHttpLogRecordExporter.builder()
                                                                            .setEndpoint(endpoint)
                                                                            .setCompression("gzip")
                                                                            .addHeader("Authorization", "Bearer " + applicationId);
        OtlpHttpLogRecordExporter exporter = builder.build();
        BatchLogRecordProcessorBuilder batchLogProcessorBuilder = BatchLogRecordProcessor.builder(exporter);
        batchLogProcessorBuilder.setMaxExportBatchSize(maxBatchSize);
        logRecordProcessor = batchLogProcessorBuilder.build();
        SdkLoggerProviderBuilder sdkLoggerProviderBuilder = SdkLoggerProvider.builder();
        sdkLoggerProviderBuilder.setResource(Resource.create(Attributes.builder()
                                                                       .put("_namespace_", namespace)
                                                                       .put("_schema_", schema)
                                                                       .build()));
        provider = sdkLoggerProviderBuilder.addLogRecordProcessor(logRecordProcessor).build();
    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        super.invoke(value, context);
        LogRecordBuilder logRecordBuilder = provider.loggerBuilder("").build().logRecordBuilder();
        logRecordBuilder.setEpoch(Instant.ofEpochMilli(value.getMetricTime()));
        logRecordBuilder.setBody("");
        AttributesBuilder attributesBuilder = Attributes.builder();
        // attribute name is the schema field name
        getBiConsumer().accept(value, attributesBuilder);
        logRecordBuilder.setAllAttributes(attributesBuilder.build());
        logRecordBuilder.emit();
    }

    @Override
    public void close() throws Exception {
        super.close();
        // Shut down the exporter
        if (logRecordProcessor != null) {
            logRecordProcessor.forceFlush();
            logRecordProcessor.close();
        }
        provider.close();
    }

    public abstract BiConsumer<T, AttributesBuilder> getBiConsumer();

}
