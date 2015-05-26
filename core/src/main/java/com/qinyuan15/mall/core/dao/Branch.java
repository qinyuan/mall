package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

import java.util.List;

/**
 * Persist Object of Branch
 * Created by qinyuan on 15-2-18.
 */
public class Branch extends PersistObject {
    private String name;
    private String logo;
    private Integer parentId;
    private String firstLetter;
    private String squareLogo;
    private String slogan;
    private String poster;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setSquareLogo(String squareLogo) {
        this.squareLogo = squareLogo;
    }

    public String getSquareLogo() {
        return squareLogo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public Integer getParentId() {
        return parentId;
    }

    private boolean parentNameInit = false;
    private String parentName;

    public synchronized String getParentName() {
        if (!parentNameInit) {
            Branch parent = new BranchDao().getInstance(this.parentId);
            parentName = parent == null ? null : parent.getName();
            parentNameInit = true;
        }
        return parentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    private List<Shoppe> shoppes;

    public synchronized List<Shoppe> getShoppes() {
        if (shoppes == null) {
            shoppes = new ShoppeDao().getInstances(this.getId());
        }
        return shoppes;
    }
}
