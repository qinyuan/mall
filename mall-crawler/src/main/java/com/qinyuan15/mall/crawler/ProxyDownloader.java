package com.qinyuan15.mall.crawler;

import com.qinyuan15.utils.http.Proxy;
import com.qinyuan15.utils.http.ProxyDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Grub Proxy information then save them to database
 * Created by qinyuan on 14-12-29.
 */
public class ProxyDownloader {
    private List<ProxyCrawler> crawlers;

    public void setCrawlers(List<ProxyCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    public void save() {
        List<Proxy> proxies = new ArrayList<>();
        for (ProxyCrawler crawler : crawlers) {
            proxies.addAll(crawler.getProxies());
        }

        for (Proxy proxy : proxies) {
            proxy.setSpeed(Integer.MAX_VALUE);
        }

        new ProxyDao().save(proxies);
    }
}
