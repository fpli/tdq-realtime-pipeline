package com.ebay.dap.tdq.flink.connector.kafka.rheos.pojo;

import com.ebay.dap.tdq.common.constant.DataCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.kafka.common.security.auth.SecurityProtocol;

import java.util.List;
import java.util.Map;

@Data
public class RheosStream {

    @JsonProperty("stream")
    private String name;

    @JsonProperty("auth-protocol")
    private SecurityProtocol authProtocol;

    private Map<DataCenter, List<String>> brokers;
}
