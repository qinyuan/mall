package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.BranchDao;
import com.qinyuan15.mall.core.dao.CommodityDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to build CommodityBranchGroup instance
 * Created by qinyuan on 15-4-30.
 */
public class CommodityBranchGroupBuilder {
    private boolean orderByCommodityCount;

    public CommodityBranchGroupBuilder orderByCommodityCount() {
        this.orderByCommodityCount = true;
        return this;
    }

    public CommodityBranchGroupBuilder orderByLetter() {
        this.orderByCommodityCount = false;
        return this;
    }

    public List<CommodityBranchGroup> build() {
        List<CommodityBranchGroup> groups = new ArrayList<>();
        CommodityDao.Factory commodityFactory = CommodityDao.factory();
        for (Branch branch : new BranchDao().getInstances()) {
            long commodityCount = commodityFactory.setBranchId(branch.getId()).getCount();
            groups.add(new CommodityBranchGroup(branch, (int) commodityCount));
        }

        if (orderByCommodityCount) {
            Collections.sort(groups, new CommodityCountComparator());
        }

        return groups;
    }

    private static class CommodityCountComparator implements Comparator<CommodityBranchGroup> {
        @Override
        public int compare(CommodityBranchGroup o1, CommodityBranchGroup o2) {
            return o2.getCommodityCount() - o1.getCommodityCount();
        }
    }

}
