package com.ebay.dap.tdq.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    /**
     * get eBay server time zone, which is at UTC-7
     *
     */
    public static ZoneId eBayServerZoneId() {
        return ZoneId.of("GMT-7");
    }


    /**
     * convert epochMilli to LocalDateTime, in eBay server's time zone, which is UTC-7
     *
     */
    public static LocalDateTime epochMilliToLocalDateTime(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), eBayServerZoneId());
    }

    /**
     * convert Instant to LocalDateTime, in eBay server's time zone, which is UTC-7
     */
    public static LocalDateTime instantToLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, eBayServerZoneId());
    }

}
