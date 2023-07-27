package com.ebay.dap.tdq.integration.pipeline;

import com.ebay.dap.tdq.common.model.avro.RheosHeader;
import com.ebay.dap.tdq.flink.common.FlinkEnv;
import com.ebay.dap.tdq.flink.function.LocalSourceFunction;
import com.ebay.dap.tdq.flink.function.SimpleLogFunction;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.avro.Schema;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticsearchSinkTestJob {
    public static void main(String[] args) throws Exception {

        FlinkEnv flinkEnv = new FlinkEnv(args);

        StreamExecutionEnvironment executionEnvironment = flinkEnv.local();

        DataStream<RheosHeader> sourceDataStream = executionEnvironment.addSource(new LocalSourceFunction())
                                                                       .name("Local Source")
                                                                       .uid("local-source")
                                                                       .setParallelism(1)
                                                                       .disableChaining();

        DataStream<RheosHeader> dataStream = sourceDataStream.map(new SimpleLogFunction<>())
                                                             .name("Log")
                                                             .uid("log")
                                                             .setParallelism(1);

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));

        // use an ElasticsearchSink.Builder to create an ElasticsearchSink
        ElasticsearchSink.Builder<RheosHeader> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts,
                new ElasticsearchSinkFunction<RheosHeader>() {
                    public IndexRequest createIndexRequest(RheosHeader element) throws JsonProcessingException {
//                        data.put("eventId", element.getEventId());
//                        data.put("eventCreateTimestamp", element.getEventCreateTimestamp());
//                        data.put("eventSentTimestamp", element.getEventSentTimestamp());

                        Schema schema = RheosHeader.getClassSchema();
                        Map<String, Object> data = new HashMap<>();
                        for (Schema.Field field : schema.getFields()) {
                            data.put(field.name(), element.get(field.name()));
                        }

                        return Requests.indexRequest()
                                       .index("my-index")
                                       .source(data);
                    }

                    @Override
                    public void process(RheosHeader element, RuntimeContext ctx, RequestIndexer indexer) {
                        try {
                            indexer.add(createIndexRequest(element));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        // configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered
        esSinkBuilder.setBulkFlushMaxActions(10);

        // provide a RestClientFactory for custom configuration on the internally created REST client
//        esSinkBuilder.setRestClientFactory(
//                restClientBuilder -> {
//                    restClientBuilder.setDefaultHeaders(...)
//                    restClientBuilder.setMaxRetryTimeoutMillis(...)
//                    restClientBuilder.setPathPrefix(...)
//                    restClientBuilder.setHttpClientConfigCallback(...)
//                }
//        );

        dataStream.addSink(esSinkBuilder.build())
                  .name("ES Sink")
                  .uid("es-sink")
                  .setParallelism(1);

        // submit flink job
        flinkEnv.execute(executionEnvironment);
    }
}
