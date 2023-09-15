package com.ebay.dap.tdq.flink.connector.pronto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProntoEnv implements Serializable {

    private String scheme;

    private String host;

    private Integer port;

    private String username;

    private String password;

}
