package com.ebay.dap.tdq.rt.domain;

import lombok.Data;

@Data
public class SimpleSojEvent {

    private String guid;

    private Integer pageId;

    private String rlogId;

    private Long eventTimestamp;

}
