package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.IProxy;
import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxyDao;
import com.qinyuan15.utils.http.ProxyPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Pool to store Proxy that get from database
 * Created by qinyuan on 14-12-31.
 */
public class DatabaseProxyPool implements ProxyPool {

    /**
     * default pool size is 200
     */
    public final static int DEFAULT_SIZE = 200;

    /**
     * default reload interval is 5 minute
     */
    public final static int DEFAULT_RELOAD_INTERVAL = 300;

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseProxyPool.class);

    private int size = DEFAULT_SIZE;
    private int reloadInterval = DEFAULT_RELOAD_INTERVAL;
    private List<Proxy> proxies;
    private int pointer = 0;

    public void setSize(int size) {
        this.size = size;
    }

    public void setReloadInterval(int reloadInterval) {
        this.reloadInterval = reloadInterval;
    }

    public void init() {
        this.reload();
        new ReloadThread().start();
    }

    private void reload() {
        ProxyDao proxyDao = new ProxyDao();
        int fastProxyCount = proxyDao.getFastCount();
        if (fastProxyCount < size) {
            this.proxies = proxyDao.getInstances(fastProxyCount);
        } else {
            this.proxies = proxyDao.getInstances(size);
        }
        this.pointer = 0;
        LOGGER.info("Reload {} proxies.", this.proxies.size());
    }

    private final Random rand = new Random();

    public synchronized Proxy next() {
        if (proxies == null || pointer >= proxies.size()) {
            this.reload();
        }
        pointer++;

        if (this.proxies.size() == 0) {
            return null;
        }

        return this.proxies.get(rand.nextInt(proxies.size()));
    }

    @Override
    public List<IProxy> next(int n) {
        List<IProxy> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(this.next());
        }
        return list;
    }

    @Override
    public int size() {
        return proxies.size();
    }

    private class ReloadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                ThreadUtils.sleep(reloadInterval);
                reload();
            }
        }
    }
}
