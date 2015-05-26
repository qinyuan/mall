package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao to deal with related commodity
 * Created by qinyuan on 15-3-15.
 */
public class RelatedCommodityDao {
    private Integer limitSize;
    private boolean inLowPrice = true;
    private boolean active = true;

    public RelatedCommodityDao setLimitSize(Integer limitSize) {
        this.limitSize = limitSize;
        return this;
    }

    public RelatedCommodityDao setInLowPrice(boolean inLowPrice) {
        this.inLowPrice = inLowPrice;
        return this;
    }

    public RelatedCommodityDao setActive(boolean active) {
        this.active = active;
        return this;
    }

    public List<Commodity> getInstances(Commodity commodity) {
        if (commodity == null || commodity.getCategoryId() == null) {
            return new ArrayList<>();
        }

        List<Commodity> commodities = CommodityDao.factory().setCategoryId(commodity
                .getCategoryId()).setInLowPrice(inLowPrice).setActive(active).getInstances();
        for (int i = 0; i < commodities.size(); i++) {
            if (commodities.get(i).getId().equals(commodity.getId())) {
                commodities.remove(i);
                break;
            }
        }

        if (IntegerUtils.isPositive(limitSize)) {
            return RandomUtils.subList(commodities, limitSize);
        } else {
            return commodities;
        }
    }
}
