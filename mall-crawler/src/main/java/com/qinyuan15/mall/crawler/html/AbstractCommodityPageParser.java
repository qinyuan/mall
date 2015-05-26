package com.qinyuan15.mall.crawler.html;

/**
 * Implement some methods of CommodityPageParser
 * Created by qinyuan on 15-1-2.
 */
abstract public class AbstractCommodityPageParser implements CommodityPageParser {
    protected final String html;

    protected AbstractCommodityPageParser(String html) {
        this.html = html;
    }
}
