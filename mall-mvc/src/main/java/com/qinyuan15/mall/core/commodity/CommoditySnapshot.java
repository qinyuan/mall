package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.branch.BranchUrlAdapter;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.core.dao.CommodityPriceDao;
import com.qinyuan15.mall.core.image.PictureUrlConverter;
import com.qinyuan15.utils.hibernate.HibernateUtils;

/**
 * Snapshot class of Commodity
 * Created by qinyuan on 15-2-27.
 */
public class CommoditySnapshot extends CommoditySimpleSnapshot {
    private Double price;
    private Branch branch;

    public CommoditySnapshot(Commodity commodity, PictureUrlConverter pictureUrlConverter) {
        super(commodity, pictureUrlConverter);
        this.price = new CommodityPriceDao().getCurrentPrice(commodity.getId());
        this.branch = HibernateUtils.get(Branch.class, commodity.getBranchId());
        BranchUrlAdapter adapter = new BranchUrlAdapter(pictureUrlConverter);
        adapter.adjustBranch(this.branch);
    }

    public Double getPrice() {
        return price;
    }

    public Branch getBranch() {
        return branch;
    }
}
