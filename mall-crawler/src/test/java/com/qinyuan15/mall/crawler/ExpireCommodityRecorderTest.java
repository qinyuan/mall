package com.qinyuan15.mall.crawler;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ExpireCommodityRecorder
 * Created by qinyuan on 15-4-7.
 */
public class ExpireCommodityRecorderTest {

    private ExpireCommodityRecorder recorder = new ExpireCommodityRecorder();

    @Test
    public void test() throws Exception {
        Integer commodityId = 10;
        for (int i = 1; i < ExpireCommodityRecorder.DEFAULT_RETRY_TIMES; i++) {
            assertThat(recorder.reachMaxFailTimes(commodityId)).isFalse();
        }
        assertThat(recorder.reachMaxFailTimes(commodityId)).isTrue();

        assertThat(recorder.reachMaxFailTimes(commodityId + 1)).isFalse();

        recorder.clear(commodityId);
        assertThat(recorder.reachMaxFailTimes(commodityId)).isFalse();
    }
}
