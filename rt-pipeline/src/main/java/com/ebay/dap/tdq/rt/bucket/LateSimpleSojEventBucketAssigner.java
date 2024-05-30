package com.ebay.dap.tdq.rt.bucket;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.ebay.dap.tdq.common.util.DateTimeUtils.eBayServerZoneId;

/**
 * Use element's timestamp field to generate bucketId
 */
public class LateSimpleSojEventBucketAssigner implements BucketAssigner<SimpleSojEvent, String> {

    @Override
    public String getBucketId(SimpleSojEvent element, Context context) {

        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(element.getEventTimestamp()), eBayServerZoneId());

        return "dt=" + time.toLocalDate().toString() + "/hr=" + time.getHour();
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
