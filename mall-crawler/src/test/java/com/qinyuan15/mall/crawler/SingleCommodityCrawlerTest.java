package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.crawler.TestCommodityPool;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.utils.http.HttpClientPool;
import org.junit.Test;

/**
 * Test SingleCommodityCrawler
 * Created by qinyuan on 15-1-11.
 */
public class SingleCommodityCrawlerTest {
    @Test
    public void test() throws Exception {
        HttpClientPool clientPool = new HttpClientPool();
        SingleCommodityCrawler crawler = new SingleCommodityCrawler(
                clientPool, new CommodityPageParserBuilder(clientPool),
                new ExpireCommodityRecorder());
        Commodity commodity = new TestCommodityPool().next();
        crawler.save(commodity);
    }
}
