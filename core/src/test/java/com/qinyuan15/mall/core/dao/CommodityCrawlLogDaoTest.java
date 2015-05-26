package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test CommodityCrawlLogDao
 * Created by qinyuan on 15-3-29.
 */
public class CommodityCrawlLogDaoTest {
    private CommodityCrawlLogDao dao = new CommodityCrawlLogDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(CommodityCrawlLogDao.factory().getInstances(0, 0).size());
    }

    @Test
    public void testLogSuccess() {
        //dao.logSuccess(3, "HelloWorld");
    }

    @Test
    public void testLogFail() {
        //dao.logFail(3, "HelloWorld2");
    }
}
