package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.Ranking;

/**
 * Persist Object of AppConfigFootLink
 * Created by qinyuan on 15-4-13.
 */
public class AppConfigFootLink extends PersistObject implements Ranking {
    private String text;
    private String link;
    private Integer ranking;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
