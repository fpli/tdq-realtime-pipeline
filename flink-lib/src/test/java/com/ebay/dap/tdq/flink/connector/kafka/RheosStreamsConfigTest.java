package com.ebay.dap.tdq.flink.connector.kafka;


import com.ebay.dap.tdq.common.constant.DataCenter;
import com.ebay.dap.tdq.flink.connector.kafka.rheos.pojo.RheosStream;
import com.ebay.dap.tdq.flink.connector.kafka.rheos.RheosStreamsConfig;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RheosStreamsConfigTest {

    @Test
    void getStream_OK() {
        RheosStream rheosStream = RheosStreamsConfig.getStream("stage", "single-dc-stream");
        assertThat(rheosStream.getName()).isEqualTo("single-dc-stream");
    }

    @Test
    void getStream_defaultEnv_OK() {
        RheosStream rheosStream = RheosStreamsConfig.getStream("prod-only-stream");
        assertThat(rheosStream.getName()).isEqualTo("prod-only-stream");
    }

    @Test
    void getStream_envNotFound() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RheosStreamsConfig.getStream("xxx", "behavior.pathfinder");
        });

        assertThat(exception).hasMessage("Cannot find env xxx");
    }

    @Test
    void getStream_streamNotFound() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RheosStreamsConfig.getStream("stage", "xxx");
        });

        assertThat(exception).hasMessage("Cannot find stream xxx");
    }

    @Test
    void getAuthProtocol_OK() {
        SecurityProtocol authProtocol = RheosStreamsConfig.getAuthProtocol("stage", "single-dc-stream");

        assertThat(authProtocol).isEqualTo(SecurityProtocol.SASL_PLAINTEXT);
    }

    @Test
    void getAuthProtocol_defaultEnv_OK() {
        SecurityProtocol authProtocol = RheosStreamsConfig.getAuthProtocol("prod-only-stream");

        assertThat(authProtocol).isEqualTo(SecurityProtocol.SASL_PLAINTEXT);
    }

    @Test
    void getBrokersAsString_OK() {
        String slcBrokers = RheosStreamsConfig.getBrokersAsString("stage", "multiple-dc-stream", DataCenter.SLC);
        String lvsBrokers = RheosStreamsConfig.getBrokersAsString("stage", "multiple-dc-stream", DataCenter.LVS);

        assertThat(slcBrokers).isEqualTo("broker1.slc.vip.ebay.com,broker2.slc.vip.ebay.com");
        assertThat(lvsBrokers).isEqualTo("broker1.lvs.vip.ebay.com,broker2.lvs.vip.ebay.com");
    }

    @Test
    void getBrokersAsString_onlyStream_OK() {
        String brokers = RheosStreamsConfig.getBrokersAsString("prod-only-stream");
        assertThat(brokers).isEqualTo("broker1.ebay.com,broker2.ebay.com");
    }

    @Test
    void getBrokersAsString_defaultEnv_OK() {
        String brokers = RheosStreamsConfig.getBrokersAsString("prod-only-stream", DataCenter.RNO);
        assertThat(brokers).isEqualTo("broker1.ebay.com,broker2.ebay.com");
    }

    @Test
    void getBrokersAsString_DCNotFound() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RheosStreamsConfig.getBrokersAsString("stage", "single-dc-stream", DataCenter.LVS);
        });

        assertThat(exception).hasMessage("Stream single-dc-stream does not contain DC lvs brokers config");
    }

    @Test
    void getBrokerList_OK() {
        List<String> brokers = RheosStreamsConfig.getBrokerList("stage", "single-dc-stream", DataCenter.SLC);

        assertThat(brokers.size()).isEqualTo(2);
    }
}