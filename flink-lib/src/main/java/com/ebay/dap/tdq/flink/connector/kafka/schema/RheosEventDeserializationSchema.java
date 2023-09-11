package com.ebay.dap.tdq.flink.connector.kafka.schema;

import io.ebay.rheos.schema.avro.RheosEventDeserializer;
import io.ebay.rheos.schema.event.RheosEvent;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class RheosEventDeserializationSchema implements KafkaRecordDeserializationSchema<RheosEvent> {

    private transient RheosEventDeserializer rheosEventDeserializer;

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);

        this.rheosEventDeserializer = new RheosEventDeserializer();
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<RheosEvent> out) throws IOException {
        RheosEvent rheosEvent = rheosEventDeserializer.deserialize(null, record.value());
        out.collect(rheosEvent);
    }

    @Override
    public TypeInformation<RheosEvent> getProducedType() {
        return TypeInformation.of(RheosEvent.class);
    }
}
