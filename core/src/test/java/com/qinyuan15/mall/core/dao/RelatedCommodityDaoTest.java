package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import java.util.List;

/**
 * Test Related
 * Created by qinyuan on 15-3-15.
 */
public class RelatedCommodityDaoTest {
    @Test
    public void testGetInstances() throws Exception {
        Commodity commodity = new CommodityDao().getInstance(8);
        List<Commodity> commodities = new RelatedCommodityDao().getInstances(commodity);
        System.out.println(commodities.size());
        for (Commodity c : commodities) {
            System.out.println(c.getId());
        }
    }
}
