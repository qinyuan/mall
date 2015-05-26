package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.CurrencyUtils;
import com.qinyuan15.utils.DateUtils;

/**
 * Persist Object of Commodity
 * Created by qinyuan on 14-12-28.
 */
public class Commodity extends SimpleCommodity {

    private String serialNumber;
    private String showId;
    private String discoverTime;
    private String buyUrl;
    private Boolean active;
    private Integer branchId;
    private String parameters;
    private Integer userId;
    private Integer sales;
    private Double price;
    private Integer categoryId;
    private Boolean inLowPrice;
    private Boolean deactivateByProgram;

    public void setDeactivateByProgram(Boolean deactivateByProgram) {
        this.deactivateByProgram = deactivateByProgram;
    }

    public Boolean getDeactivateByProgram() {
        return deactivateByProgram;
    }

    public Boolean getInLowPrice() {
        return inLowPrice;
    }

    public void setInLowPrice(Boolean inLowPrice) {
        this.inLowPrice = inLowPrice;
    }

    public Double getPrice() {
        return price == null ? null : CurrencyUtils.trimCent(price);
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParameters() {
        return parameters;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getShowId() {
        return showId;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getDiscoverTime() {
        return DateUtils.getDatePart(discoverTime);
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public Branch getBranch() {
        return new BranchDao().getInstance(this.branchId);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setDiscoverTime(String discoverTime) {
        this.discoverTime = discoverTime;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOnShelfTime() {
        PriceRecord firstPriceRecord = new PriceRecordDao().getFirstInstance(getId());
        if (firstPriceRecord == null) {
            return null;
        }

        return firstPriceRecord.getRecordTime().toString();
    }
}
