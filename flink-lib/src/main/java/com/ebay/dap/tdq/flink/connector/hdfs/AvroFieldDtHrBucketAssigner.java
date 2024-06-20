package com.ebay.dap.tdq.flink.connector.hdfs;

import com.ebay.dap.tdq.common.util.DateTimeUtils;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

import java.time.LocalDateTime;


/**
 * Use element's timestamp field to generate bucketId
 */
public class AvroFieldDtHrBucketAssigner<T extends SpecificRecord> implements BucketAssigner<T, String> {

    private final String tsField;

    public AvroFieldDtHrBucketAssigner(String tsField) {
        this.tsField = tsField;
    }

    @Override
    public String getBucketId(T element, Context context) {

        Schema.Field ts = element.getSchema().getField(tsField);

        long val = Long.parseLong(String.valueOf(element.get(ts.pos())));

        LocalDateTime time = DateTimeUtils.epochMilliToLocalDateTime(val);

        return "dt=" + time.toLocalDate().toString() + "/hr=" + time.getHour();
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
