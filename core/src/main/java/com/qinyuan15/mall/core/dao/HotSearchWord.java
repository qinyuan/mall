package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.Ranking;

/**
 * Persist object of hot search word
 * Created by qinyuan on 15-2-28.
 */
public class HotSearchWord extends PersistObject implements Ranking {
    private String content;
    private Integer categoryId;
    private Integer ranking;
    private Boolean hot;

    public String getContent() {
        return content;
    }

    public Integer getRanking() {
        return ranking;
    }

    public Boolean getHot() {
        return hot;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
