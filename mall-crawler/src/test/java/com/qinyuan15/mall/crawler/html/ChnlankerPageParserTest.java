package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.test.TestFileUtils;
import org.junit.Test;

import java.util.List;

/**
 * Test ChnlankerPageParser
 * Created by qinyuan on 15-5-17.
 */
public class ChnlankerPageParserTest {
    @Test
    public void testGetProxies() throws Exception {
        ChnlankerPageParser pageParser = new ChnlankerPageParser();
        pageParser.setHTML(TestFileUtils.read("chnlanker.html"));
        List<Proxy> proxies = pageParser.getProxies();
        System.out.println(proxies.size());
    }
}
