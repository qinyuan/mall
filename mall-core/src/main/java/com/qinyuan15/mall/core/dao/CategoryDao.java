package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.PersistObjectUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao object of Category
 * Created by qinyuan on 15-2-24.
 */
public class CategoryDao {
    private final static String PARENT_ID = "parentId";

    public List<Category> getInstances() {
        List<Category> categories = new ArrayList<>();
        for (Category category : getRootInstances()) {
            categories.add(category);
            for (Category subCategory : getSubInstances(category.getId())) {
                categories.add(subCategory);
            }
        }
        return categories;
    }

    public Category getInstance(Integer id) {
        return HibernateUtils.get(Category.class, id);
    }

    public String getNameById(Integer id) {
        Category category = this.getInstance(id);
        return category == null ? null : category.getName();
    }

    public Integer add(String name, Integer parentId) {
        Category category = new Category();
        category.setName(name);
        category.setParentId(parentId);
        return new RankingDao().add(category);
    }

    private final static String ROOT_CONDITION = PARENT_ID + " IS NULL OR " + PARENT_ID + "<=0";

    public Category getFirstInstance() {
        return HibernateUtils.getFirstItem(Category.class,
                ROOT_CONDITION + RankingDao.ASC_ORDER);
    }

    public List<Category> getRootInstances() {
        return HibernateUtils.getList(Category.class, ROOT_CONDITION + RankingDao.ASC_ORDER);
    }

    public List<Category> getSubInstances(int parentId) {
        return HibernateUtils.getList(Category.class, PARENT_ID + "=" + parentId + RankingDao.ASC_ORDER);
    }

    public List<Category> getSubInstancesAndSelf(int parentId) {
        return HibernateUtils.getList(Category.class,
                "id=" + parentId + " OR " + PARENT_ID + "=" + parentId + RankingDao.ASC_ORDER);
    }

    public String getSubInstancesAndSelfIdsString(int parentId) {
        List<Category> categories = getSubInstancesAndSelf(parentId);
        return PersistObjectUtils.getIdsString(categories);
    }

    public boolean isUsed(int id) {
        return hasCommodity(id) || hasSubInstance(id);
    }

    public boolean hasSubInstance(int id) {
        return HibernateUtils.getCount(Category.class, PARENT_ID + "=" + id) > 0;
    }

    public boolean hasCommodity(int id) {
        return HibernateUtils.getCount(Commodity.class, "categoryId=" + id) > 0;
    }

    public void delete(int id) {
        new CommodityDao().unlinkCategory(id);
        new HotSearchWordDao().clear(id);
        new CategoryBranchDao().deleteByCategoryId(id);
        HibernateUtils.delete(Category.class, id);

        for (Category subCategory : getSubInstances(id)) {
            this.delete(subCategory.getId());
        }
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(Category.class, id, PARENT_ID);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(Category.class, id, PARENT_ID);
    }
}
