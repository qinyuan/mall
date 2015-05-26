package com.qinyuan15.mall.crawler.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to validate commodity page
 * Created by qinyuan on 15-5-18.
 */
public class CommodityPageValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPageParser.class);

    /**
     * validate if current page is correct
     *
     * @param pageParser commodity page parser
     * @param url        commodity page url
     * @return true if current page is correct
     */
    public boolean validate(CommodityPageParser pageParser, String url) {
        String urlInPage = pageParser.getUrl();
        if (url.equals(urlInPage)) {
            return true;
        } else {
            LOGGER.warn("the url of commodity is {} but the url from page is {}",
                    url, urlInPage);
            return false;
        }
    }
}
