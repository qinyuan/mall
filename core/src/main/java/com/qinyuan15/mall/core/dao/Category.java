package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.Ranking;
/**
 * Persist object of category
 * Created by qinyuan on 15-2-25.
 */
public class Category extends PersistObject implements Ranking {
    private String name;
    private Integer parentId;
    private Integer ranking;

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    private boolean initParentName = false;
    private String parentName;

    public synchronized String getParentName() {
        if (!this.initParentName) {
            Category category = HibernateUtils.get(Category.class, this.parentId);
            this.parentName = category == null ? null : category.getName();
            this.initParentName = true;
        }
        return this.parentName;
    }
}
