package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.mall.crawler.http.DatabaseProxyPool;
import com.qinyuan15.utils.http.HttpClientPool;
import org.junit.Test;

/**
 * Test EtaoCommodityDetailImagesParser
 * Created by qinyuan on 15-5-15.
 */
public class EtaoCommodityDetailImagesParserTest {
    @Test
    public void testGetDetailImages() throws Exception {
        String dataUrl = "http://dsc.taobaocdn.com/i5/400/800/40780735321/TB1KQ99HXXXXXXpXXXX8qtpFXXX.desc%7Cvar%5Edesc%3Bsign%5E571c7e3d64bcc501b48d31e8c8c9c7cd%3Blang%5Egb18030%3Bt%5E1427898201";
        HttpClientPool httpClientPool = new HttpClientPool();
        httpClientPool.setProxyPool(new DatabaseProxyPool());
        EtaoCommodityDetailImagesParser parser = new EtaoCommodityDetailImagesParser(
                httpClientPool, dataUrl);
        for(String str:parser.getDetailImages()) {
            System.out.println(str);
        }
    }
}
