package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.Instant;

public class UpdateProcessTimeFunction implements MapFunction<SimpleSojEvent, SimpleSojEvent> {

    @Override
    public SimpleSojEvent map(SimpleSojEvent value) throws Exception {
        value.setProcessTime(Instant.now().toEpochMilli());
        return value;
    }
}
