package com.ebay.dap.tdq.rt.key;

import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;

public class SimpleSignalDeltaKeySelector implements KeySelector<SimpleSignalDelta, Tuple3<String, String, String>> {

    @Override
    public Tuple3<String, String, String> getKey(SimpleSignalDelta signalDelta) throws Exception {
        return Tuple3.of(signalDelta.getName(), signalDelta.getEventSource(), signalDelta.getEventType());
    }

}
