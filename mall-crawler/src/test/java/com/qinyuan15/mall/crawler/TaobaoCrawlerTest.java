package com.qinyuan15.mall.crawler;

import com.qinyuan15.utils.http.HttpClientPool;
import org.junit.Test;

/**
 * Test TaobaoCrawler
 * Created by qinyuan on 15-5-12.
 */
public class TaobaoCrawlerTest {
    @Test
    public void testGetParameters() throws Exception {
        String url = "http://detail.tmall.com/item.htm?id=44202510076";
        TaobaoCrawler crawler = new TaobaoCrawler(url, new HttpClientPool());
        System.out.println(crawler.getParameters());
    }
}
