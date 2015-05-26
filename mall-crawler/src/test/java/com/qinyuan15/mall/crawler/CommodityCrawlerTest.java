package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.TestCommodityPool;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import org.junit.Test;

/**
 * Test PriceHisotryCrawler
 * Created by qinyuan on 15-1-11.
 */
public class CommodityCrawlerTest {

    @Test
    public void test() {
        CommodityCrawler crawler = new CommodityCrawler();
        crawler.setCommodityPool(new TestCommodityPool());
        crawler.setCommodityPageParserBuilder(new CommodityPageParserBuilder(null));
        crawler.init();
        ThreadUtils.sleep(20);
    }
}
