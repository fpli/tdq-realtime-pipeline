package com.ebay.dap.tdq.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    /**
     * get eBay server time zone, which is at UTC-7
     *
     * @return
     */
    public static ZoneId eBayServerZoneId() {
        return ZoneId.of("GMT-7");
    }


    /**
     * convert timestamp to LocalDateTime, with eBay server time zone
     *
     * @param ts
     * @return
     */
    public static LocalDateTime tsToLocalDateTime(long ts) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ts), eBayServerZoneId());
    }

}
