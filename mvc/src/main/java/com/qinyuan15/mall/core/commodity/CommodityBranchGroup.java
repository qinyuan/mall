package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Branch;

/**
 * Class to group commodity by branch
 * Created by qinyuan on 15-4-30.
 */
public class CommodityBranchGroup {
    private Branch branch;
    private int commodityCount;

    public Branch getBranch() {
        return branch;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    CommodityBranchGroup(Branch branch, int commodityCount) {
        this.branch = branch;
        this.commodityCount = commodityCount;
    }
}
