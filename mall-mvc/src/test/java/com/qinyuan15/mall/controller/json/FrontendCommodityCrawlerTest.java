package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Test FrontendCommodityCrawler
 * Created by qinyuan on 15-5-12.
 */
public class FrontendCommodityCrawlerTest {
    private FrontendCommodityCrawler crawler;

    @Before
    public void setUp() throws Exception {
        crawler = new FrontendCommodityCrawler(null, new CommodityPageParserBuilder(null));
    }

    @Test
    public void testGet() throws Exception {
        FrontendCommodityCrawler.CommodityJson json = crawler.get(null,
                "44202510076", "http://s.etao.com/detail/44202510076.html");
        System.out.println(json.success);
        System.out.println(json.detail);
    }

    @Test
    public void testGet2() throws Exception {
        String[] showIds = {"44202510076", "43823326756", "44948151912", "44178031917"};
        String[] urls = {
                "http://s.etao.com/detail/44202510076.html",
                "http://s.etao.com/detail/43823326756.html",
                "http://s.etao.com/detail/44948151912.html",
                "http://s.etao.com/detail/44178031917.html"
        };
        System.out.println(crawler.get(showIds, urls).size());
    }
}
