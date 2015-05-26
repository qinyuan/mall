package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.parameters.CommodityParametersCrawler;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.junit.Test;

/**
 * Test CommodityParametersCrawler
 * Created by qinyuan on 15-5-16.
 */
public class CommodityParametersCrawlerTest {
    @Test
    public void testRunJob() throws Exception {
        CommodityParametersCrawler crawler = new CommodityParametersCrawler(new HttpClientPool());
        crawler.start();
        crawler.stopSafely();
        ThreadUtils.sleep(10);
    }
}
