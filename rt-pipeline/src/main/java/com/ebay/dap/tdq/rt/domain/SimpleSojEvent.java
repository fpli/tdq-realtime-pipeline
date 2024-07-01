package com.ebay.dap.tdq.rt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SimpleSojEvent extends KafkaMessage {

    private String eventPrimaryId;

    private String guid;

    private Integer pageId;

    private Integer siteId;

    private Integer appId;

    private Integer bot;

    private String eventFamily;

    private String eventAction;

    private Long eventTimestamp;

    private Long processTime;

}
