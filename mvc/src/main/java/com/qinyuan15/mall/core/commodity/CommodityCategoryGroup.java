package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Category;

/**
 * Class to group commodity by category
 * Created by qinyuan on 15-5-3.
 */
public class CommodityCategoryGroup {
    private Category category;
    private int commodityCount;

    public Category getCategory() {
        return category;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    CommodityCategoryGroup(Category category, int commodityCount) {
        this.category = category;
        this.commodityCount = commodityCount;
    }
}
