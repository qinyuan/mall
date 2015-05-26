package com.qinyuan15.mall.core.branch;

import com.qinyuan15.mall.core.commodity.CommodityBranchGroup;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.image.PictureUrlConverter;

import java.util.List;

/**
 * Adjust url of branch
 * Created by qinyuan on 15-2-25.
 */
public class BranchUrlAdapter {
    private PictureUrlConverter pictureUrlConverter;

    public BranchUrlAdapter(PictureUrlConverter pictureUrlConverter) {
        this.pictureUrlConverter = pictureUrlConverter;
    }

    public List<Branch> adjustBranches(List<Branch> branches) {
        for (Branch branch : branches) {
            adjustBranch(branch);
        }
        return branches;
    }

    public List<CommodityBranchGroup> adjustCommodityBranchGroups(List<CommodityBranchGroup> commodityBranchGroups) {
        for (CommodityBranchGroup commodityBranchGroup : commodityBranchGroups) {
            adjustBranch(commodityBranchGroup.getBranch());
        }
        return commodityBranchGroups;
    }

    public List<BranchGroup> adjustBranchGroups(List<BranchGroup> branchGroups) {
        for (BranchGroup branchGroup : branchGroups) {
            adjustBranches(branchGroup.getBranches());
        }
        return branchGroups;
    }

    public Branch adjustBranch(Branch branch) {
        if (branch == null) {
            return null;
        }
        branch.setLogo(this.pictureUrlConverter.pathToUrl(branch.getLogo()));
        branch.setSquareLogo(this.pictureUrlConverter.pathToUrl(branch.getSquareLogo()));
        branch.setPoster(this.pictureUrlConverter.pathToUrl(branch.getPoster()));
        return branch;
    }
}
