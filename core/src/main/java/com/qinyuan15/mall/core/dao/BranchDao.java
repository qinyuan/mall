package com.qinyuan15.mall.core.dao;

import com.google.common.collect.Lists;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.PersistObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao object of branch
 * Created by qinyuan on 15-2-24.
 */
public class BranchDao {
    private final static String ORDER_CLAUSE = " ORDER BY firstLetter ASC, id ASC";

    public Branch getInstance(Integer id) {
        return HibernateUtils.get(Branch.class, id);
    }

    public boolean isUsed(Integer id) {
        return hasCommodity(id) || hasSubInstance(id);
    }

    public boolean hasCommodity(Integer id) {
        return HibernateUtils.getCount(Commodity.class, "branchId=" + id) > 0;
    }

    public boolean hasSubInstance(Integer id) {
        return HibernateUtils.getCount(Branch.class, "parentId=" + id) > 0;
    }

    public void delete(Integer id) {
        new CommodityDao().unlinkBranch(id);
        new CategoryBranchDao().deleteByBranchId(id);
        HibernateUtils.delete(Branch.class, id);
        for (Branch subBranch : getSubInstances(id)) {
            this.delete(subBranch.getId());
        }
    }

    public List<Branch> getInstances() {
        return HibernateUtils.getList(Branch.class, ORDER_CLAUSE);
    }

    public List<Branch> getRootInstances() {
        return HibernateUtils.getList(Branch.class,
                "parentId IS NULL OR parentId<=0" + ORDER_CLAUSE);
    }

    public List<Branch> getSubInstances(int parentId) {
        return HibernateUtils.getList(Branch.class, "parentId=" + parentId + ORDER_CLAUSE);
    }

    public List<Branch> getAllSubInstances(int parentId) {
        List<Branch> allBranches = new ArrayList<>();
        List<Branch> subBranches = getSubInstances(parentId);
        if (subBranches.size() > 0) {
            allBranches.addAll(subBranches);
            for (Branch branch : subBranches) {
                allBranches.addAll(getAllSubInstances(branch.getId()));
            }
        }
        return allBranches;
    }

    public List<Branch> getAllSubInstancesAndSelf(int parentId) {
        List<Branch> branches = Lists.newArrayList(getInstance(parentId));
        branches.addAll(getAllSubInstances(parentId));
        return branches;
    }

    public String getAllSubInstancesAndSelfIdsString(int parentId) {
        List<Branch> branches = getAllSubInstancesAndSelf(parentId);
        return PersistObjectUtils.getIdsString(branches);
    }

    public List<Branch> getInstancesByCategoryId(int categoryId) {
        List<Branch> branches = new ArrayList<>();
        for (CategoryBranch categoryBranch : new CategoryBranchDao().getInstances(categoryId)) {
            branches.add(this.getInstance(categoryBranch.getBranchId()));
        }
        return branches;
    }
}
