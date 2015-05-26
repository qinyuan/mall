package com.qinyuan15.mall.crawler;

import com.qinyuan15.utils.http.Proxy;

import java.util.List;

/**
 * Grab proxies information from certain page
 * Created by qinyuan on 14-12-29.
 */
public interface ProxyCrawler {
    List<Proxy> getProxies();
}
