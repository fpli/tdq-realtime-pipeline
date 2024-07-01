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

        SimpleSojEvent simpleSojEvent = convertToSimpleSojEvent(event);

        // add extra kafka metadata
        simpleSojEvent.setKafkaPartition(record.partition());
        simpleSojEvent.setKafkaOffset(record.offset());
        simpleSojEvent.setKafkaTimestamp(record.timestamp());

        out.collect(simpleSojEvent);
    }

    private SimpleSojEvent convertToSimpleSojEvent(GenericRecord record) {

        SimpleSojEvent simpleSojEvent = new SimpleSojEvent();

        simpleSojEvent.setEventTimestamp((Long) record.get("eventTimestamp"));
        simpleSojEvent.setGuid(record.get("guid") == null ? "" : record.get("guid").toString());
        simpleSojEvent.setPageId((Integer) record.get("pageId"));
        simpleSojEvent.setSiteId(record.get("siteId") == null ? -1 : (Integer) record.get("siteId"));
        simpleSojEvent.setAppId(record.get("appId") == null ? -1 : (Integer) record.get("appId"));
        simpleSojEvent.setBot(0); // non-bot only

        Map<String, String> applicationPayload = (Map<String, String>) record.get("applicationPayload");
        simpleSojEvent.setEventPrimaryId(applicationPayload.get("eventPrimaryId"));

        simpleSojEvent.setProcessTime(System.currentTimeMillis());

        return simpleSojEvent;
    }

    @Override
    public TypeInformation<SimpleSojEvent> getProducedType() {
        return TypeInformation.of(SimpleSojEvent.class);
    }
}
