package com.ebay.dap.tdq.rt.source;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import io.ebay.rheos.kafka.client.StreamConnectorConfig;
import io.ebay.rheos.schema.avro.RheosEventDeserializer;
import io.ebay.rheos.schema.avro.SchemaRegistryAwareAvroDeserializerHelper;
import io.ebay.rheos.schema.event.RheosEvent;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleSojEventDeserializationSchema implements KafkaRecordDeserializationSchema<SimpleSojEvent> {

    private final String registryUrl;
    private transient SchemaRegistryAwareAvroDeserializerHelper<GenericRecord> deserializerHelper;
    private transient RheosEventDeserializer rheosEventDeserializer;


    public SimpleSojEventDeserializationSchema(String registryUrl) {
        this.registryUrl = registryUrl;
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        this.rheosEventDeserializer = new RheosEventDeserializer();
        Map<String, Object> config = new HashMap<>();
        config.put(StreamConnectorConfig.RHEOS_SERVICES_URLS, this.registryUrl);
        this.deserializerHelper = new SchemaRegistryAwareAvroDeserializerHelper<>(config, GenericRecord.class);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<SimpleSojEvent> out) throws IOException {

        RheosEvent rheosEvent = rheosEventDeserializer.deserialize(null, record.value());

        GenericRecord event = deserializerHelper.deserializeData(rheosEvent.getSchemaId(), rheosEvent.toBytes());

        SimpleSojEvent simpleSojEvent = new SimpleSojEvent();

        simpleSojEvent.setPageId((Integer) event.get("pageId"));
        simpleSojEvent.setEventTimestamp((Long) event.get("eventTimestamp"));
        simpleSojEvent.setGuid(event.get("guid") == null ? null : event.get("guid").toString());
        simpleSojEvent.setRlogId(event.get("rlogid") == null ? null : event.get("rlogid").toString());
        simpleSojEvent.setSiteId(event.get("siteId") == null ? -1 : Integer.parseInt(event.get("siteId").toString()));
        simpleSojEvent.setAppId(event.get("appId") == null ? -1 : Integer.parseInt(event.get("appId").toString()));
        simpleSojEvent.setProcessTime(System.currentTimeMillis());

        out.collect(simpleSojEvent);
    }

    @Override
    public TypeInformation<SimpleSojEvent> getProducedType() {
        return TypeInformation.of(SimpleSojEvent.class);
    }
}
