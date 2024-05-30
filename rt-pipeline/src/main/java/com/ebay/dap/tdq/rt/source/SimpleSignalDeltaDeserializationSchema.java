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
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.IOException;


@Slf4j
public class SimpleSignalDeltaDeserializationSchema implements KafkaRecordDeserializationSchema<SimpleSignalDelta> {


    private transient DatumReader<Signal> reader;

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        log.info("Initialize SojEvent DatumReader.");
        reader = new SpecificDatumReader<>(Signal.class);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<SimpleSignalDelta> out) throws IOException {
        try {
            Decoder decoder = DecoderFactory.get().binaryDecoder(record.value(), null);
            Signal signalDelta = reader.read(null, decoder);

            SimpleSignalDelta simpleSignalDelta = convertToSimpleSignalDelta(signalDelta);

            out.collect(simpleSignalDelta);
        } catch (IOException e) {
            throw new SerializationException("Error when deserializing SojEvent.", e);
        }
    }

    @Override
    public TypeInformation<SimpleSignalDelta> getProducedType() {
        return TypeInformation.of(SimpleSignalDelta.class);
    }

    private SimpleSignalDelta convertToSimpleSignalDelta(Signal signalDelta) {
        SimpleSignalDelta simpleSignalDelta = new SimpleSignalDelta();

        // set timestamp
        simpleSignalDelta.setTimestamp(signalDelta.getSignalInfo().getDeltaTimestamp());

        // set name
        simpleSignalDelta.setName(signalDelta.getSignalInfo().getName());

        Field eventSource = signalDelta.getSignalInfo().getFields().get("unifiedEvent.eventSource");
        simpleSignalDelta.setEventSource(eventSource.getValue());

        Field eventType = signalDelta.getSignalInfo().getFields().get("unifiedEvent.eventType");
        simpleSignalDelta.setEventType(eventType.getValue());

//        Field eactn = signalDelta.getSignalInfo().getFields().get("eactn");
//        simpleSignalDelta.setEventAction(eactn.getValue());

        return simpleSignalDelta;
    }
}
