package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test AppConfigDetailImageDao
 * Created by qinyuan on 15-4-5.
 */
public class AppConfigDetailImageDaoTest {

    private AppConfigDetailImageDao dao = new AppConfigDetailImageDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(dao.getInstances().size());
    }

    @Test
    public void testAdd() throws Exception {
        //dao.add("hello", "world");
    }
}
