package com.ebay.dap.tdq.rt.watermark;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

public class SimpleSojEventTimestampAssigner implements SerializableTimestampAssigner<SimpleSojEvent> {

    @Override
    public long extractTimestamp(SimpleSojEvent simpleSojEvent, long l) {
        return simpleSojEvent.getEventTimestamp();
    }
}
