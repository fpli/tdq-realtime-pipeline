package com.ebay.dap.tdq.rt.key;

import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple4;

public class SimpleSignalDeltaKeySelector implements KeySelector<SimpleSignalDelta, Tuple4<String, String, Integer, Integer>> {

    @Override
    public Tuple4<String, String, Integer, Integer> getKey(SimpleSignalDelta signalDelta) throws Exception {
        return Tuple4.of(signalDelta.getSignalName(), signalDelta.getEventSource(), signalDelta.getPageId(), signalDelta.getSiteId());
    }

}
