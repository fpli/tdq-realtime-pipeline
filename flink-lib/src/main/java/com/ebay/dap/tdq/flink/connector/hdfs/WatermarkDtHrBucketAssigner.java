package com.ebay.dap.tdq.flink.connector.hdfs;

import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WatermarkDtHrBucketAssigner<T> implements BucketAssigner<T, String> {

    @Override
    public String getBucketId(T element, Context context) {
        if (Long.MIN_VALUE == context.currentWatermark()) {
            // no watermark
            throw new IllegalStateException("No watermark found");
        }

        long wm = context.currentWatermark();
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(wm), ZoneId.of("GMT-7"));

        return "dt=" + time.toLocalDate().toString() + "/hr=" + time.getHour();
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
