package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.Ranking;

/**
 * Persist object of category and poster
 * Created by qinyuan on 15-3-28.
 */
public class CategoryPoster extends PersistObject implements Ranking {
    private Integer categoryId;
    private String path;
    private String link;
    private Integer ranking;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getPath() {
        return path;
    }

    public String getLink() {
        return link;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private boolean categoryInit = false;
    private Category category;

    public synchronized Category getCategory() {
        if (!this.categoryInit) {
            this.category = new CategoryDao().getInstance(this.categoryId);
            this.categoryInit = true;
        }
        return this.category;
    }

    public String getCategoryName() {
        Category category = this.getCategory();
        return category == null ? null : category.getName();
    }
}
