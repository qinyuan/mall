package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObject;
import com.qinyuan15.utils.hibernate.Ranking;
/**
 * Persist object of category and branch
 * Created by qinyuan on 15-3-25.
 */
public class CategoryBranch extends PersistObject implements Ranking {
    private Integer categoryId;
    private Integer branchId;
    private Integer ranking;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    private boolean categoryInit = false;
    private Category category;

    public synchronized Category getCategory() {
        if (!this.categoryInit) {
            this.category = new CategoryDao().getInstance(this.categoryId);
            this.categoryInit = true;
        }
        return this.category;
    }

    public String getCategoryName() {
        Category category = this.getCategory();
        return category == null ? null : category.getName();
    }

    private boolean branchInit = false;
    private Branch branch;

    public synchronized Branch getBranch() {
        if (!this.branchInit) {
            this.branch = new BranchDao().getInstance(this.branchId);
            this.branchInit = true;
        }
        return this.branch;
    }

    public String getBranchName() {
        Branch branch = this.getBranch();
        return branch == null ? null : branch.getName();
    }
}
