package com.ebay.dap.tdq.rt.function;

import com.ebay.dap.tdq.rt.domain.PageMetric;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;

@Slf4j
public class PageMetricProntoSinkFunction implements ElasticsearchSinkFunction<PageMetric> {


    private final String indexPrefix;

    private final Set<Integer> top50PageIds = Sets.newHashSet(
            2047675,2047937,2048309,2052300,2058891,2062857,2065432,2349624,2351460,2353552,2355844,2367289,
            2376473,2380424,2380676,2380866,2481888,2487283,2489527,2492446,2492448,2492450,2493971,2506613,2510300,
            2512348,2524149,2543286,2543464,2544535,2553215,2565282,2565297,2566055,3196657,3216531,3243862,3289402,
            3418065,3658866,3748097,3820580,3851689,3853885,4256617,4268716,4375194,4380942,4429486,4451299
    );

    public PageMetricProntoSinkFunction(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }


    @Override
    public void process(PageMetric element, RuntimeContext ctx, RequestIndexer indexer) {
        try {
            indexer.add(createIndexRequest(element));

            // log top50 pageId record
            if (top50PageIds.contains(element.getPageId())) {
                log.info("Sinked pageId {} metric to Pronto, event count is {}, dt is {}, hr is {}.",
                        element.getPageId(), element.getEventCount(), element.getDt(), element.getHr());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public IndexRequest createIndexRequest(PageMetric element) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("page_id", element.getPageId());
        data.put("rt_event_cnt", element.getEventCount());
        data.put("acc_rt_event_cnt", element.getEventCount());
        data.put("batch_event_cnt", -1);
        data.put("dt", element.getDt());
        data.put("hr", element.getHr());
        data.put("metric_time", element.getMetricTime());
        data.put("metric_key", "hourly_event_cnt");

//        String id = generateDocId(element);

        Instant now = Instant.now();
        data.put("create_time", now.toString());

        return Requests.indexRequest()
                       .index(indexPrefix)
                       .setPipeline("calculate_ingest_lag")
                       .source(data);
    }

    private String generateDocId(PageMetric element) {
        String str = element.getPageId() + "." + element.getDt() + "." + element.getHr();
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
}
