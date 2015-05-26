package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Persist Object of AppConfigDetailImage
 * Created by qinyuan on 15-4-5.
 */
public class AppConfigDetailImage extends PersistObject {
    private String path;
    private String link;

    public String getPath() {
        return path;
    }

    public String getLink() {
        return link;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
