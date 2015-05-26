package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

/**
 * Dao of IndexLogo
 * Created by qinyuan on 15-3-19.
 */
public class IndexLogoDao {
    public List<IndexLogo> getInstances() {
        return new RankingDao().getInstances(IndexLogo.class);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(IndexLogo.class, id);
    }

    public Integer add(String path, String link, String description) {
        IndexLogo indexLogo = new IndexLogo();
        indexLogo.setPath(path);
        indexLogo.setLink(link);
        indexLogo.setDescription(description);
        return new RankingDao().add(indexLogo);
    }

    public IndexLogo getInstance(int id) {
        return HibernateUtils.get(IndexLogo.class, id);
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(IndexLogo.class, id);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(IndexLogo.class, id);
    }
}
