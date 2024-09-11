package com.ebay.dap.tdq.rt.source;

import com.ebay.dap.tdq.common.model.avro.Field;
import com.ebay.dap.tdq.common.model.avro.Signal;
import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.metrics.Counter;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;


@Slf4j
public class SimpleSignalDeltaDeserializationSchema implements KafkaRecordDeserializationSchema<SimpleSignalDelta> {


    private transient DatumReader<Signal> reader;
    private Counter eventCounter;

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        log.info("Initialize SojEvent DatumReader.");
        reader = new SpecificDatumReader<>(Signal.class);

        eventCounter = context.getMetricGroup()
                              .addGroup("tdq_rt")
                              .counter("read_delta_cnt");
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<SimpleSignalDelta> out) throws IOException {

        Decoder decoder = DecoderFactory.get().binaryDecoder(record.value(), null);
        Signal signalDelta = reader.read(null, decoder);

        SimpleSignalDelta simpleSignalDelta = convertToSimpleSignalDelta(signalDelta);

        simpleSignalDelta.setKafkaPartition(record.partition());
        simpleSignalDelta.setKafkaOffset(record.offset());
        simpleSignalDelta.setKafkaTimestamp(record.timestamp());

        eventCounter.inc();
        out.collect(simpleSignalDelta);
    }

    @Override
    public TypeInformation<SimpleSignalDelta> getProducedType() {
        return TypeInformation.of(SimpleSignalDelta.class);
    }

    private SimpleSignalDelta convertToSimpleSignalDelta(Signal signalDelta) {
        SimpleSignalDelta simpleSignalDelta = new SimpleSignalDelta();

        // set timestamp
        simpleSignalDelta.setTimestamp(signalDelta.getSignalInfo().getDeltaTimestamp());

        // parse signal name
        simpleSignalDelta.setSignalName(signalDelta.getSignalInfo().getName());

        // parse pageId
        Integer pageId = signalDelta.getSignalInfo().getContext().getPageInteractionContext().getPageId();
        simpleSignalDelta.setPageId(pageId == null ? -1 : pageId);

        // parse siteId
        simpleSignalDelta.setSiteId(signalDelta.getSignalInfo().getContext().getPageInteractionContext().getSiteId());

        Field eventSource = signalDelta.getSignalInfo().getFields().get("unifiedEvent.eventSource");
        simpleSignalDelta.setEventSource(eventSource == null ? "" : eventSource.getValue());

        Field eventPrimaryId = signalDelta.getSignalInfo().getFields().get("eventPrimaryId");
        simpleSignalDelta.setEventPrimaryId(eventPrimaryId == null ? "" : eventPrimaryId.getValue());

        Field unifiedEventId = signalDelta.getSignalInfo().getFields().get("unifiedEventId");
        simpleSignalDelta.setUnifiedEventId(unifiedEventId == null ? "" : unifiedEventId.getValue());

        simpleSignalDelta.setProcessTime(System.currentTimeMillis());

        return simpleSignalDelta;
    }
}
