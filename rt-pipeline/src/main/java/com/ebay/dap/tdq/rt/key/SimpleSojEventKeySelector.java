package com.ebay.dap.tdq.rt.key;

import com.ebay.dap.tdq.rt.domain.SimpleSojEvent;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;

public class SimpleSojEventKeySelector implements KeySelector<SimpleSojEvent, Tuple3<Integer, Integer, Integer>> {

    @Override
    public Tuple3<Integer, Integer, Integer> getKey(SimpleSojEvent simpleSojEvent) throws Exception {
        return Tuple3.of(simpleSojEvent.getPageId(), simpleSojEvent.getSiteId(), simpleSojEvent.getAppId());
    }
}
