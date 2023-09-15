package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PageMetricProntoSinkFunction implements ElasticsearchSinkFunction<PageMetric> {


    private final String indexPrefix;

    public PageMetricProntoSinkFunction(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }


    @Override
    public void process(PageMetric element, RuntimeContext ctx, RequestIndexer indexer) {
        try {
            indexer.add(createIndexRequest(element));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public IndexRequest createIndexRequest(PageMetric element) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("page_id", element.getPageId());
        data.put("event_cnt", element.getEventCount());
        data.put("dt", element.getDt());
        data.put("hr", element.getHr());

        data.put("metric_key", "hourly_event_cnt");

        String id = generateDocId(element);

        Instant now = Instant.now();
        data.put("create_time", now.toString());

        return Requests.indexRequest()
                       .index(indexPrefix)
                       .id(id)
                       .setPipeline("calculate_ingest_lag")
                       .source(data);
    }

    private String generateDocId(PageMetric element) {
        String str = element.getPageId() + "." + element.getDt() + "." + element.getHr();
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
}
