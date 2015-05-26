package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxyDao;

import java.util.List;

/**
 * Class to test slow proxies
 * Created by qinyuan on 15-5-16.
 */
public class SlowProxiesTester extends StoptableThread {
    //private final static int TEST_SIZE = 200;
    private final ProxyTester proxyTester;
    private final static int INTERVAL = 1800;

    public SlowProxiesTester(String testUrl) {
        proxyTester = new ProxyTester(testUrl, new DatabaseProxySpeedRecorder());
    }

    @Override
    protected void jobToRun() {
        if (needToTest()) {
            List<Proxy> proxies = new ProxyDao().getSlowInstances();
            for (Proxy proxy : proxies) {
                proxyTester.test(proxy);
            }
            ThreadUtils.sleep(INTERVAL);
        }
    }

    private boolean needToTest() {
        ProxyDao proxyDao = new ProxyDao();
        return proxyDao.getCount() / proxyDao.getSlowCount() < 2;
    }
}
