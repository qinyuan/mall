package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Category;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.mall.core.dao.CommodityDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to build CommodityCategoryGroup instance
 * Created by qinyuan on 15-5-3.
 */
public class CommodityCategoryGroupBuilder {
    private boolean orderByCommodityCount;

    public CommodityCategoryGroupBuilder orderByCommodityCount() {
        this.orderByCommodityCount = true;
        return this;
    }

    public CommodityCategoryGroupBuilder orderRanking() {
        this.orderByCommodityCount = false;
        return this;
    }

    public List<CommodityCategoryGroup> build() {
        List<CommodityCategoryGroup> groups = new ArrayList<>();
        CommodityDao.Factory commodityFactory = CommodityDao.factory();
        for (Category category : new CategoryDao().getInstances()) {
            long commodityCount = commodityFactory.setCategoryId(category.getId()).getCount();
            groups.add(new CommodityCategoryGroup(category, (int) commodityCount));
        }

        if (orderByCommodityCount) {
            Collections.sort(groups, new CommodityCountComparator());
        }

        return groups;
    }

    private static class CommodityCountComparator implements Comparator<CommodityCategoryGroup> {
        @Override
        public int compare(CommodityCategoryGroup o1, CommodityCategoryGroup o2) {
            return o2.getCommodityCount() - o1.getCommodityCount();
        }
    }

}
