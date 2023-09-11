package com.ebay.dap.tdq.integration.pipeline;

import com.ebay.dap.tdq.common.model.avro.RheosHeader;
import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.connector.hdfs.AvroFieldDtHrBucketAssigner;
import com.ebay.dap.tdq.flink.connector.hdfs.ParquetAvroWritersWithCompression;
import com.ebay.dap.tdq.integration.function.LocalSourceFunction;
import com.ebay.dap.tdq.integration.function.SimpleLogFunction;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;

import static com.ebay.dap.tdq.common.constant.Property.FLINK_APP_SINK_HDFS_PATH;

public class HdfsSinkTestJob {

    public static void main(String[] args) throws Exception {

        FlinkEnv flinkEnv = new FlinkEnv(args);

        StreamExecutionEnvironment executionEnvironment = flinkEnv.init();

        DataStream<RheosHeader> sourceDataStream = executionEnvironment.addSource(new LocalSourceFunction())
                                                                       .name("Local Source")
                                                                       .uid("local-source")
                                                                       .setParallelism(1)
                                                                       .disableChaining();

        DataStream<RheosHeader> dataStream = sourceDataStream.map(new SimpleLogFunction<>())
                                                             .name("Log")
                                                             .uid("log")
                                                             .setParallelism(1);


        final StreamingFileSink<RheosHeader> hdfsSink = StreamingFileSink
                .forBulkFormat(new Path(flinkEnv.getString(FLINK_APP_SINK_HDFS_PATH)),
                        ParquetAvroWritersWithCompression.forSpecificRecord(RheosHeader.class))
                .withBucketAssigner(new AvroFieldDtHrBucketAssigner<>("eventCreateTimestamp"))
                .build();

        dataStream.addSink(hdfsSink)
                  .name("HDFS Sink")
                  .uid("hdfs-sink")
                  .setParallelism(1);

        // submit flink job
        flinkEnv.execute(executionEnvironment);
    }

}
