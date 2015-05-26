package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Persist Object of commodity crawl log
 * Created by qinyuan on 15-2-29.
 */
public class CommodityCrawlLog extends PersistObject {
    private Integer commodityId;
    private String action;
    private String logTime;
    private Boolean success;

    public Integer getCommodityId() {
        return commodityId;
    }

    public String getAction() {
        return action;
    }

    public String getLogTime() {
        return DateUtils.adjustDateStringFromDB(logTime);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private Commodity commodity;
    private boolean commodityInit;

    public synchronized Commodity getCommodity() {
        if (!this.commodityInit) {
            this.commodity = new CommodityDao().getInstance(this.commodityId);
            this.commodityInit = true;
        }
        return this.commodity;
    }

    public String getCommodityName() {
        return this.getCommodity().getName();
    }
}
