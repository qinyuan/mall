package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.Ranking;

/**
 * Persist Object of IndexLogo
 * Created by qinyuan on 15-2-18.
 */
public class IndexLogo extends PersistObject implements Ranking {
    private String path;
    private String link;
    private Integer ranking;
    private String description;

    public String getPath() {
        return path;
    }

    public String getLink() {
        return link;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
