package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test SimpleCommodityDao
 * Created by qinyuan on 15-4-19.
 */
public class SimpleCommodityDaoTest {
    private SimpleCommodityDao dao = new SimpleCommodityDao();
    private CommodityDao commodityDao = new CommodityDao();

    @Test
    public void testGetInstances() throws Exception {
        for (int i = 1; i < 50; i++) {
            assertThat(dao.getInstances(i).size()).isLessThanOrEqualTo(i);
        }
        for (SimpleCommodity commodity : dao.getInstances(10)) {
            int id = commodity.getId();
            assertThat(commodity.getName()).isEqualTo(commodityDao.getInstance(id).getName());
            assertThat(commodity.getUrl()).isEqualTo(commodityDao.getInstance(id).getUrl());
            assertThat(commodity.getCrawlDate()).isEqualTo(commodityDao.getInstance(id).getCrawlDate());
            System.out.println(commodity.getCrawlDate());
        }
    }

    @Test
    public void testGetActiveInstances() throws Exception {
        System.out.println(dao.getActiveInstances().size());
    }
}
