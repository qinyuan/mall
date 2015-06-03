package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.http.IProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to validate commodity page
 * Created by qinyuan on 15-5-18.
 */
public class CommodityPageValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPageParser.class);

    private final static Map<String, Integer> failRecordMap = new HashMap<>();

    public static synchronized int getFailTimes(IProxy proxy) {
        String url = proxy.getHost() + ":" + proxy.getPort();
        Integer failTimes = failRecordMap.get(url);
        if (failTimes == null) {
            failTimes = 0;
        }

        failTimes++;
        failRecordMap.put(url, failTimes);
        return failTimes;
    }

    public static synchronized void resetFailTimes(IProxy proxy) {
        String url = proxy.getHost() + ":" + proxy.getPort();
        failRecordMap.remove(url);
    }

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
