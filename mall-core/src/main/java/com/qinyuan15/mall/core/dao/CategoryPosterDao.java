package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

/**
 * Dao of CategoryPoster
 * Created by qinyuan on 15-3-28.
 */
public class CategoryPosterDao {
    private final static String CATEGORY_ID = "categoryId";

    public List<CategoryPoster> getInstances(int categoryId) {
        return HibernateUtils.getList(CategoryPoster.class,
                CATEGORY_ID + "=" + categoryId + RankingDao.ASC_ORDER);
    }

    public CategoryPoster getInstance(Integer id) {
        return HibernateUtils.get(CategoryPoster.class, id);
    }

    public void update(Integer id, Integer categoryId, String path, String link) {
        CategoryPoster categoryPoster = this.getInstance(id);
        categoryPoster.setCategoryId(categoryId);
        categoryPoster.setPath(path);
        categoryPoster.setLink(link);
        HibernateUtils.update(categoryPoster);
    }

    public Integer add(Integer categoryId, String path, String link) {
        CategoryPoster categoryPoster = new CategoryPoster();
        categoryPoster.setCategoryId(categoryId);
        categoryPoster.setPath(path);
        categoryPoster.setLink(link);
        return new RankingDao().add(categoryPoster);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(CategoryPoster.class, id);
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(CategoryPoster.class, id, CATEGORY_ID);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(CategoryPoster.class, id, CATEGORY_ID);
    }
}
