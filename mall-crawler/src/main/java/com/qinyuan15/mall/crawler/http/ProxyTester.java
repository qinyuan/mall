package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxyRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to test proxy
 * Created by qinyuan on 15-5-16.
 */
public class ProxyTester {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProxyTester.class);
    private final String testPage;
    private final ProxyRecorder proxyRecorder = new DatabaseProxyRecorder();
    private final PageValidator pageValidator;

    public ProxyTester(String testPage) {
        this(testPage, null);
    }

    public ProxyTester(String testPage, PageValidator pageValidator) {
        this.testPage = testPage;
        this.pageValidator = pageValidator;
    }

    public void test(Proxy proxy) {
        HttpClient client = new HttpClient();
        client.setProxy(proxy);
        int speed = Integer.MAX_VALUE;
        try {
            LOGGER.info("start testing {} with page {}.", proxy, testPage);
            String content = client.getContent(testPage);
            LOGGER.info("connect to {} with proxy {} in {} milliseconds",
                    testPage, proxy, client.getLastConnectTime());
            if (pageValidator == null || pageValidator.validate(content)) {
                speed = client.getLastConnectTime();
            }
        } catch (Throwable e) {
            LOGGER.warn("fail to connect {} with proxy {}: {}",
                    testPage, proxy, e);
        }
        proxyRecorder.recordSpeed(proxy, speed);
    }
}
