package com.ebay.dap.tdq.rt.bucket;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import com.ebay.dap.tdq.rt.domain.SimpleSignalDelta;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleSignalDeltaBucketAssigner implements BucketAssigner<SimpleSignalDelta, String> {

    private final boolean isLate;

    public SimpleSignalDeltaBucketAssigner(boolean isLate) {
        this.isLate = isLate;
    }

    @Override
    public String getBucketId(SimpleSignalDelta element, Context context) {

        final DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        final DateTimeFormatter hrFmt = DateTimeFormatter.ofPattern("HH");
        String dtStr;
        String hrStr;

        long epochMilli = isLate ? context.currentWatermark() : element.getTimestamp();

        if (epochMilli > 0) {
            LocalDateTime dt = DateTimeUtils.epochMilliToLocalDateTime(epochMilli);
            dtStr = dt.format(dtFmt);
            hrStr = dt.format(hrFmt);
        } else {
            dtStr = "19700101";
            hrStr = "00";
        }

        return "dt=" + dtStr + "/hr=" + hrStr;
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
