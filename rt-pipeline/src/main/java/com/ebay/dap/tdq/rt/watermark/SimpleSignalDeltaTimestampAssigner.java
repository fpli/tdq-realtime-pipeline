package com.ebay.dap.tdq.rt.watermark;

import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

public class SimpleSignalDeltaTimestampAssigner implements SerializableTimestampAssigner<SimpleSignalDelta> {

    @Override
    public long extractTimestamp(SimpleSignalDelta element, long recordTimestamp) {
        return element.getTimestamp();
    }
}
