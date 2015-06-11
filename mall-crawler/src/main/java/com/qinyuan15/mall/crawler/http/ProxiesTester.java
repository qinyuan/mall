package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class to test Proxy
 * Created by qinyuan on 15-1-2.
 */
public class ProxiesTester {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProxiesTester.class);

    public final static String DEFAULT_TEST_PAGE = "www.baidu.com";
    public final static int DEFAULT_THREAD_SIZE = 15;
    private final static PageValidator baiduValidator = new BaiduPageValidator();

    private String testPage = DEFAULT_TEST_PAGE;
    private int threadSize = DEFAULT_THREAD_SIZE;
    private List<Proxy> proxies;

    public void setTestPage(String testPage) {
        this.testPage = testPage;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    private int proxyPointer;

    private void initProxies() {
        this.proxies = new ProxyDao().getInstances();
        this.proxyPointer = 0;
    }

    private synchronized Proxy nextProxy() {
        if (this.proxyPointer >= this.proxies.size()) {
            return null;
        } else {
            return this.proxies.get(this.proxyPointer++);
        }
    }

    public void run() {
        LOGGER.info("proxy test start!");
        initProxies();
        for (int i = 0; i < threadSize; i++) {
            new TestThread(i).start();
            ThreadUtils.sleep(0.25);
        }
        LOGGER.info("proxy test complete!");
    }

    private class TestThread extends Thread {
        int index;

        private TestThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                Proxy proxy = nextProxy();
                if (proxy == null) {
                    LOGGER.info("proxy test thread {} complete.", this.index);
                    break;
                }
                new ProxyTester(testPage, baiduValidator).test(proxy);
            }
        }
    }
}
