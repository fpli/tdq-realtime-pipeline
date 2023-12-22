package com.ebay.dap.tdq.integration.function;

import com.ebay.dap.tdq.common.avro.SojEvent;
import org.apache.flink.api.common.functions.FilterFunction;

public class SojEventSamplerFunction implements FilterFunction<SojEvent> {

    private final double sampleRate;

    public SojEventSamplerFunction(double sampleRate) {
        this.sampleRate = sampleRate * 100;
    }

    @Override
    public boolean filter(SojEvent sojEvent) throws Exception {
        return sojEvent.getGuid().hashCode() % 100 <= sampleRate;
    }
}
