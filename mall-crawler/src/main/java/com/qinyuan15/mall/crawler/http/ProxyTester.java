package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxySpeedRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to test proxy
 * Created by qinyuan on 15-5-16.
 */
public class ProxyTester {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProxyTester.class);
    private final String testPage;
    private final ProxySpeedRecorder proxySpeedRecorder;

    public ProxyTester(String testPage, ProxySpeedRecorder proxySpeedRecorder) {
        this.testPage = testPage;
        this.proxySpeedRecorder = proxySpeedRecorder;
    }

    public void test(Proxy proxy) {
        HttpClient client = new HttpClient();
        client.setProxy(proxy);
        int speed;
        try {
            LOGGER.info("start testing {} with page {}.", proxy, testPage);
            client.getContent(testPage);
            LOGGER.info("connect to {} with proxy {} in {} milliseconds",
                    testPage, proxy, client.getLastConnectTime());
            speed = client.getLastConnectTime();
        } catch (Throwable e) {
            speed = Integer.MAX_VALUE;
            LOGGER.warn("fail to connect {} with proxy {}: {}",
                    testPage, proxy, e);
        }
        proxySpeedRecorder.recordSpeed(proxy, speed);
    }
}
