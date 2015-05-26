package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Commodity class that contains only necessary fields for crawler
 * Created by qinyuan on 15-4-19.
 */
public class SimpleCommodity extends PersistObject {
    private String url;
    private String name;
    private String crawlDate;

    public String getCrawlDate() {
        return crawlDate;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setCrawlDate(String crawlDate) {
        this.crawlDate = crawlDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
}
