package com.ebay.dap.tdq.rt.sink;

import com.ebay.dap.tdq.rt.domain.pronto.SojMetricDoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.connector.elasticsearch.sink.ElasticsearchEmitter;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class SojMetricProntoEmitter implements ElasticsearchEmitter<SojMetricDoc> {

    private final String indexPrefix;
    private transient ObjectMapper objectMapper;

    public SojMetricProntoEmitter(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    @Override
    public void open() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void emit(SojMetricDoc sojMetricDoc, SinkWriter.Context context, org.apache.flink.connector.elasticsearch.sink.RequestIndexer indexer) {
        try {
            indexer.add(createIndexRequest(sojMetricDoc));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public IndexRequest createIndexRequest(SojMetricDoc sojMetricDoc) throws JsonProcessingException {

        sojMetricDoc.setUpstreamProcessTime(System.currentTimeMillis());
        String id = generateDocId(sojMetricDoc);

        String jsonStr = objectMapper.writeValueAsString(sojMetricDoc);

        return Requests.indexRequest()
                       .index(indexPrefix)
                       .setPipeline("calculate_soj_metric_ingest_lag")
                       .id(id)
                       .source(jsonStr);
    }

    private String generateDocId(SojMetricDoc sojMetricDoc) {
        String str = String.join(".",
                String.valueOf(sojMetricDoc.getPageId()),
                String.valueOf(sojMetricDoc.getSiteId()),
                String.valueOf(sojMetricDoc.getAppId()),
                String.valueOf(sojMetricDoc.getBot()));

        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }


}
