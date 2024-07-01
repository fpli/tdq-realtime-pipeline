package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import io.ebay.rheos.kafka.client.StreamConnectorConfig;
import io.ebay.rheos.schema.avro.SchemaRegistryAwareAvroDeserializerHelper;
import io.ebay.rheos.schema.event.RheosEvent;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class RheosEventToSimpleSojEventMapFunction extends RichMapFunction<RheosEvent, SimpleSojEvent> {

    private final String registryUrl;
    private transient SchemaRegistryAwareAvroDeserializerHelper<GenericRecord> deserializerHelper;

    public RheosEventToSimpleSojEventMapFunction(String registryUrl) {
        this.registryUrl = registryUrl;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Map<String, Object> config = new HashMap<>();
        config.put(StreamConnectorConfig.RHEOS_SERVICES_URLS, this.registryUrl);
        this.deserializerHelper = new SchemaRegistryAwareAvroDeserializerHelper<>(config, GenericRecord.class);
    }

    @Override
    public SimpleSojEvent map(RheosEvent rheosEvent) throws Exception {

        GenericRecord event = deserializerHelper.deserializeData(rheosEvent.getSchemaId(), rheosEvent.toBytes());

        SimpleSojEvent simpleSojEvent = new SimpleSojEvent();

        simpleSojEvent.setPageId((Integer) event.get("pageId"));
        simpleSojEvent.setEventTimestamp((Long) event.get("eventTimestamp"));
        simpleSojEvent.setGuid((String) event.get("guid"));

        return simpleSojEvent;
    }

}
