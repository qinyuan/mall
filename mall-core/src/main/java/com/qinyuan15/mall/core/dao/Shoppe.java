package com.qinyuan15.mall.core.dao;
import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Persist object of Shoppe
 * Created by qinyuan on 15-3-1.
 */
public class Shoppe extends PersistObject {
    private String name;
    private String url;
    private Integer branchId;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}
