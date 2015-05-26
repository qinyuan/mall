package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test UserLogDao
 * Created by qinyuan on 15-3-6.
 */
public class UserLogDaoTest {
    @Test
    public void testGetInstances() throws Exception {
        System.out.println(UserLogDao.factory().getInstances(0, 0).size());
    }

    @Test
    public void testGetCount() {
        System.out.println(UserLogDao.factory().getCount());
    }

    @Test
    public void testSave() throws Exception {
        //dao.save(1, "测试");
    }
}
