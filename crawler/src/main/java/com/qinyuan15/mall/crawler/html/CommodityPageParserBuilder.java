package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.http.HttpClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to build commodity page parser
 * Created by qinyuan on 15-5-14.
 */
public class CommodityPageParserBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPageParser.class);
    private final static String ETAO_PREFIX = "http://s.etao.com";

    private final HttpClientPool httpClientPool;

    public CommodityPageParserBuilder() {
        this(null);
    }

    public CommodityPageParserBuilder(HttpClientPool httpClientPool) {
        this.httpClientPool = httpClientPool;
    }

    public CommodityPageParser build(String url, String html) {
        if (url == null) {
            return null;
        }

        if (url.startsWith(ETAO_PREFIX)) {
            return new EtaoCommodityPageParser(html, httpClientPool);
        }

        if (url.contains(ETAO_PREFIX)) {
            return new EtaoCommodityPageParser(html, httpClientPool);
        }

        LOGGER.warn("no suitable web page parser for url {}", url);
        return null;
    }
}
