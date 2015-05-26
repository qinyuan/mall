package com.qinyuan15.mall.core.dao;

import java.util.List;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

/**
 * Dao of HotSearchWord
 * Created by qinyuan on 15-2-28.
 */
public class HotSearchWordDao {
    private final static String CATEGORY_ID = "categoryId";

    public void delete(Integer id) {
        HibernateUtils.delete(HotSearchWord.class, id);
    }

    public Integer add(String content, Integer categoryId, Boolean hot) {
        HotSearchWord hotSearchWord = new HotSearchWord();
        hotSearchWord.setContent(content);
        hotSearchWord.setCategoryId(categoryId);
        hotSearchWord.setHot(hot);
        return new RankingDao().add(hotSearchWord);
    }

    public void update(Integer id, String content, Integer categoryId, Boolean hot) {
        HotSearchWord hotSearchWord = getInstance(id);
        hotSearchWord.setContent(content);
        hotSearchWord.setHot(hot);

        if (!hotSearchWord.getCategoryId().equals(categoryId)) {
            Integer maxRanking = new RankingDao().getMaxRanking(HotSearchWord.class);
            if (!maxRanking.equals(hotSearchWord.getRanking())) {
                hotSearchWord.setRanking(maxRanking + 1);
            }
            hotSearchWord.setCategoryId(categoryId);
        }

        HibernateUtils.update(hotSearchWord);
    }

    public HotSearchWord getInstance(Integer id) {
        return HibernateUtils.get(HotSearchWord.class, id);
    }

    public List<HotSearchWord> getInstances(Integer categoryId) {
        return HibernateUtils.getList(HotSearchWord.class,
                CATEGORY_ID + "=" + categoryId + RankingDao.ASC_ORDER);
    }

    public void clear(int categoryId) {
        HibernateUtils.delete(HotSearchWord.class, CATEGORY_ID + "=" + categoryId);
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(HotSearchWord.class, id, CATEGORY_ID);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(HotSearchWord.class, id, CATEGORY_ID);
    }
}
