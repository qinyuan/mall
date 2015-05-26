package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Dao of CategoryBranch
 * Created by qinyuan on 15-3-25.
 */
public class CategoryBranchDao {
    private final static String CATEGORY_ID = "categoryId";
    private final static String BRANCH_ID = "branchId";

    public List<CategoryBranch> getInstances(int categoryId) {
        return HibernateUtils.getList(CategoryBranch.class,
                CATEGORY_ID + "=" + categoryId + RankingDao.ASC_ORDER);
    }

    public CategoryBranch getInstance(int categoryId, int branchId) {
        String whereClause = CATEGORY_ID + "=" + categoryId + " AND " +
                BRANCH_ID + "=" + branchId + RankingDao.ASC_ORDER;
        return HibernateUtils.getFirstItem(CategoryBranch.class, whereClause);
    }

    public List<Integer> add(Integer categoryId, List<Integer> branchIds) {
        List<Integer> ids = new ArrayList<>();
        for (Integer branchId : branchIds) {
            if (this.getInstance(categoryId, branchId) == null) {
                ids.add(this.add(categoryId, branchId));
            }
        }
        return ids;
    }

    public Integer add(Integer categoryId, Integer branchId) {
        CategoryBranch categoryBranch = new CategoryBranch();
        categoryBranch.setCategoryId(categoryId);
        categoryBranch.setBranchId(branchId);
        return new RankingDao().add(categoryBranch);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(CategoryBranch.class, id);
    }

    public void deleteByCategoryId(Integer categoryId) {
        HibernateUtils.delete(CategoryBranch.class, CATEGORY_ID + "=" + categoryId);
    }

    public void deleteByBranchId(Integer branchId) {
        HibernateUtils.delete(CategoryBranch.class, BRANCH_ID + "=" + branchId);
    }

    public CategoryBranch getInstance(int id) {
        return HibernateUtils.get(CategoryBranch.class, id);
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(CategoryBranch.class, id, CATEGORY_ID);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(CategoryBranch.class, id, CATEGORY_ID);
    }
}
