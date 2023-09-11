package com.ebay.dap.tdq.common.constant;

public class Property {

    // ----------------------- kafka property -----------------------
    // kafka consumer
    public static final String KAFKA_CONSUMER_PARTITION_DISCOVERY_INTERVAL_MS = "kafka.consumer.partition-discovery-interval-ms";
    public static final String KAFKA_CONSUMER_MAX_POLL_RECORDS = "kafka.consumer.max-poll-records";
    public static final String KAFKA_CONSUMER_RECEIVE_BUFFER = "kafka.consumer.receive-buffer";
    public static final String KAFKA_CONSUMER_FETCH_MAX_BYTES = "kafka.consumer.fetch-max-bytes";
    public static final String KAFKA_CONSUMER_FETCH_MAX_WAIT_MS = "kafka.consumer.fetch-max-wait-ms";
    public static final String KAFKA_CONSUMER_MAX_PARTITIONS_FETCH_BYTES = "kafka.consumer.max-partitions-fetch-bytes";
    public static final String KAFKA_CONSUMER_PARTITION_ASSIGNOR_CLASS = "kafka.consumer.partition-assignor-class";
    public static final String KAFKA_CONSUMER_AUTO_OFFSET_RESET = "kafka.consumer.auto-offset-reset";

    // kafka producer
    public static final String KAFKA_PRODUCER_BATCH_SIZE = "kafka.producer.batch-size";
    public static final String KAFKA_PRODUCER_REQUEST_TIMEOUT_MS = "kafka.producer.request-timeout-ms";
    public static final String KAFKA_PRODUCER_DELIVERY_TIMEOUT_MS = "kafka.producer.delivery-timeout-ms";
    public static final String KAFKA_PRODUCER_REQUEST_RETRIES = "kafka.producer.request-retries";
    public static final String KAFKA_PRODUCER_LINGER_MS = "kafka.producer.linger-ms";
    public static final String KAFKA_PRODUCER_BUFFER_MEMORY = "kafka.producer.buffer-memory";
    public static final String KAFKA_PRODUCER_ACK = "kafka.producer.ack";
    public static final String KAFKA_PRODUCER_COMPRESSION_TYPE = "kafka.producer.compression-type";
    public static final String KAFKA_PRODUCER_MAX_REQUEST_SIZE = "kafka.producer.max-request-size";

    // rheos
    public static final String RHEOS_REGISTRY_URL = "rheos.registry-url";
    public static final String RHEOS_CLIENT_AUTH_TYPE = "rheos.client.auth-type";
    public static final String RHEOS_CLIENT_IAF_ID = "rheos.client.iaf.id";
    public static final String RHEOS_CLIENT_IAF_SECRET = "rheos.client.iaf.secret";
    public static final String RHEOS_CLIENT_IAF_ENV = "rheos.client.iaf.env";
    public static final String RHEOS_CLIENT_TF_APP_ID = "rheos.client.tf.app-id";
    public static final String RHEOS_CLIENT_TF_ENV = "rheos.client.tf.env";


    // ----------------------- flink property -----------------------
    // flink - app
    public static final String FLINK_APP_NAME = "flink.app.name";
    public static final String DEBUG_MODE = "flink.app.debug-mode";

    // flink checkpoint
    public static final String FLINK_APP_CHECKPOINT_DATA_DIR = "flink.app.checkpoint.data-dir";
    public static final String FLINK_APP_CHECKPOINT_INTERVAL_MS = "flink.app.checkpoint.interval-ms";
    public static final String FLINK_APP_CHECKPOINT_TIMEOUT_MS = "flink.app.checkpoint.timeout-ms";
    public static final String FLINK_APP_CHECKPOINT_MIN_PAUSE_BETWEEN_MS = "flink.app.checkpoint.min-pause-between-ms";
    public static final String FLINK_APP_CHECKPOINT_MAX_CONCURRENT = "flink.app.checkpoint.max-concurrent";
    public static final String FLINK_APP_CHECKPOINT_TOLERATE_FAILURE_COUNT = "flink.app.checkpoint.tolerate-failure-count";


    // flink source
    public static final String FLINK_APP_SOURCE_KAFKA_ENV = "flink.app.source.kafka.env";
    public static final String FLINK_APP_SOURCE_KAFKA_STREAM = "flink.app.source.kafka.stream";
    public static final String FLINK_APP_SOURCE_KAFKA_DC = "flink.app.source.kafka.dc";
    public static final String FLINK_APP_SOURCE_KAFKA_TOPIC = "flink.app.source.kafka.topic";
    public static final String FLINK_APP_SOURCE_KAFKA_GROUP_ID = "flink.app.source.kafka.group-id";
    public static final String FLINK_APP_SOURCE_KAFKA_FROM_TIMESTAMP = "flink.app.source.kafka.from-timestamp";

    // flink watermark
    public static final String FLINK_APP_WATERMARK_MAX_OUT_OF_ORDERNESS_IN_MIN = "flink.app.watermark.max-out-of-orderness-in-min";
    public static final String FLINK_APP_WATERMARK_IDLE_SOURCE_TIMEOUT_IN_MIN = "flink.app.watermark.idle-source-timeout-in-min";

    // flink sink kafka
    public static final String FLINK_APP_SINK_KAFKA_DEFAULT_TOPIC = "flink.app.sink.kafka.default-topic";
    public static final String FLINK_APP_SINK_KAFKA_ENV = "flink.app.sink.kafka.env";
    public static final String FLINK_APP_SINK_KAFKA_STREAM = "flink.app.sink.kafka.stream";
    public static final String FLINK_APP_SINK_KAFKA_DC = "flink.app.sink.kafka.dc";

    // flink hdfs sink
    public static final String FLINK_APP_SINK_HDFS_PATH = "flink.app.sink.hdfs.path";


    // flink - parallelism
    public static final String FLINK_APP_PARALLELISM_DEFAULT = "flink.app.parallelism.default";
    public static final String FLINK_APP_PARALLELISM_SOURCE = "flink.app.parallelism.source";
    public static final String FLINK_APP_PARALLELISM_SINK = "flink.app.parallelism.sink";


    // rest client
    public static final String REST_BASE_URL = "rest-client.base-url";
    public static final String REST_USERNAME = "rest-client.username";
    public static final String REST_CONFIG_PULL_INTERVAL = "rest-client.config.pull-interval";
    public static final String REST_CONFIG_ENV = "rest-client.config.env";
    public static final String REST_CONFIG_PROFILE = "rest-client.config.profile";

    // pronto
    public static final String PRONTO_SCHEME = "pronto.scheme";
    public static final String PRONTO_HOST = "pronto.host";
    public static final String PRONTO_PORT = "pronto.port";
    public static final String PRONTO_USERNAME = "pronto.username";
    public static final String PRONTO_PASSWORD = "pronto.password";

}
