package com.ebay.dap.tdq.rt.bucket;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

import java.time.LocalDateTime;

/**
 * Use element's timestamp field to generate bucketId
 */
public class SimpleSojEventBucketAssigner implements BucketAssigner<SimpleSojEvent, String> {

    private final boolean isLate;

    public SimpleSojEventBucketAssigner(boolean isLate) {
        this.isLate = isLate;
    }


    @Override
    public String getBucketId(SimpleSojEvent element, Context context) {
        LocalDateTime time = DateTimeUtils.epochMilliToLocalDateTime(element.getEventTimestamp());
        return "dt=" + time.toLocalDate().toString() + "/hr=" + time.getHour();
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
