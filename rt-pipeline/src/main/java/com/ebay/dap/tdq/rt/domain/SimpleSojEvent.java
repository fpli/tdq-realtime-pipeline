package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleSojEvent {

    private String guid;

    private Integer pageId;

    private String rlogId;

    private Integer siteId;

    private Integer appId;

    private String eventFamily;

    private String eventAction;

    private Long eventTimestamp;

    private Long processTime;

}
