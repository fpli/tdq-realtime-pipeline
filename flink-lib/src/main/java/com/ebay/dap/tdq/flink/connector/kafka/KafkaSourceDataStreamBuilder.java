package com.ebay.dap.tdq.flink.connector.kafka;

import com.ebay.dap.tdq.common.constant.DataCenter;
import com.google.common.base.Preconditions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;


public class KafkaSourceDataStreamBuilder<T> {

    private final StreamExecutionEnvironment environment;

    private String rheosStream;
    private DataCenter dc;
    private String operatorName;
    private String uid;
    private String slotGroup;
    private int parallelism = 1;
    private int outOfOrderlessInMin;
    private String fromTimestamp = "latest";
    private int idleSourceTimeout;
    private boolean rescaled = false;

    public KafkaSourceDataStreamBuilder(StreamExecutionEnvironment environment) {
        this.environment = environment;
    }

    public KafkaSourceDataStreamBuilder<T> dc(DataCenter dc) {
        this.dc = dc;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> operatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> parallelism(int parallelism) {
        this.parallelism = parallelism;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> uid(String uid) {
        this.uid = uid;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> slotGroup(String slotGroup) {
        this.slotGroup = slotGroup;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> rescaled(boolean rescaled) {
        this.rescaled = rescaled;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> outOfOrderlessInMin(int outOfOrderlessInMin) {
        this.outOfOrderlessInMin = outOfOrderlessInMin;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> fromTimestamp(String fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
        return this;
    }

    public KafkaSourceDataStreamBuilder<T> idleSourceTimeout(int idleSourceTimeout) {
        this.idleSourceTimeout = idleSourceTimeout;
        return this;
    }

    public DataStream<T> build(KafkaDeserializationSchema<T> schema) {
        Preconditions.checkNotNull(dc);
        return this.build(schema, dc, operatorName, parallelism, uid, slotGroup, rescaled);
    }

    public DataStream<T> buildRescaled(KafkaDeserializationSchema<T> schema) {
        Preconditions.checkNotNull(dc);
        return this.build(schema, dc, operatorName, parallelism, uid, slotGroup, true);
    }

    public DataStream<T> build(KafkaDeserializationSchema<T> schema, DataCenter dc,
                               String operatorName, int parallelism, String uid, String slotGroup,
                               boolean rescaled) {
        Preconditions.checkNotNull(dc);
//        KafkaConsumerConfig config = KafkaConsumerConfig.ofDC(dc);
//        FlinkKafkaSourceConfigWrapper configWrapper = new FlinkKafkaSourceConfigWrapper(
//                config, outOfOrderlessInMin, idleSourceTimeout, fromTimestamp);
//        FlinkKafkaConsumerFactory factory = new FlinkKafkaConsumerFactory(configWrapper);
//
//        DataStream<T> dataStream = environment
//                .addSource(factory.get(schema))
//                .setParallelism(parallelism)
//                .slotSharingGroup(slotGroup)
//                .name(operatorName)
//                .uid(uid);
//
//        if (rescaled) {
//            return dataStream.rescale();
//        }

        return null;
    }
}
