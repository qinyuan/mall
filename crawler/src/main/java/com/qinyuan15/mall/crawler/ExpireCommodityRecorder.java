package com.qinyuan15.mall.crawler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to record expired commodity
 * Created by qinyuan on 15-4-7.
 */
public class ExpireCommodityRecorder {
    public final static int DEFAULT_RETRY_TIMES = 3;
    private Map<Integer, Integer> failTimesMap = new ConcurrentHashMap<>();
    private final int retryTimes;

    public ExpireCommodityRecorder(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public ExpireCommodityRecorder() {
        this(DEFAULT_RETRY_TIMES);
    }

    public Integer getFailTimes(Integer commodityId) {
        Integer failTimes = failTimesMap.get(commodityId);
        return failTimes == null ? 0 : failTimes;
    }

    public synchronized boolean reachMaxFailTimes(Integer commodityId) {
        Integer failTimes = getFailTimes(commodityId);
        failTimesMap.put(commodityId, ++failTimes);
        return failTimes >= retryTimes;
    }

    public void clear(Integer commodityId) {
        failTimesMap.put(commodityId, 0);
    }
}
