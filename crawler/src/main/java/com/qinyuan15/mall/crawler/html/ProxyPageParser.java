package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.http.Proxy;

import java.util.List;

/**
 * Parse Proxy information from proxy page
 * Created by qinyuan on 14-12-29.
 */
public interface ProxyPageParser {
    void setHTML(String html);

    List<Proxy> getProxies();

    List<String> getSubLinks();
}
