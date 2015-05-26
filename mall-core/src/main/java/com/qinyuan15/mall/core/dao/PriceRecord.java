package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.CurrencyUtils;

import java.sql.Date;
import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Class to record price information
 * Created by qinyuan on 15-1-4.
 */
public class PriceRecord extends PersistObject {
    private int commodityId;
    private Date recordTime;
    private double price;
    private Date grabTime;

    public int getCommodityId() {
        return commodityId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public double getPrice() {
        return CurrencyUtils.trimCent(price);
    }

    public Date getGrabTime() {
        return grabTime;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGrabTime(Date grabTime) {
        this.grabTime = grabTime;
    }
}
