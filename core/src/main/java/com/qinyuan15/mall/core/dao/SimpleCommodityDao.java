package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao about simple commodity
 * Created by qinyuan on 15-4-19.
 */
public class SimpleCommodityDao {

    public List<SimpleCommodity> getActiveInstances() {
        String hql = "SELECT id,name,url,crawlDate " +
                "FROM Commodity WHERE active=true";

        return getInstances(hql, -1);
    }

    public List<SimpleCommodity> getInstances(int maxSize) {
        String hql = "SELECT id,name,url,crawlDate " +
                "FROM Commodity WHERE active=true AND crawlDate<>date(now()) " +
                "ORDER BY rand()";

        return getInstances(hql, maxSize);
    }

    private List<SimpleCommodity> getInstances(String hql, int size) {
        @SuppressWarnings("unchecked")
        List<Object[]> list = HibernateUtils.getList(hql, 0, size);

        List<SimpleCommodity> commodities = new ArrayList<>();
        for (Object[] item : list) {
            SimpleCommodity commodity = new SimpleCommodity();

            commodity.setId((Integer) item[0]);
            commodity.setName((String) item[1]);
            commodity.setUrl((String) item[2]);
            commodity.setCrawlDate((String) item[3]);

            commodities.add(commodity);
        }

        return commodities;
    }
}
