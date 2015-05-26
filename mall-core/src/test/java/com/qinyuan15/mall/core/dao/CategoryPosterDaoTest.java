package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test CategoryPosterDao
 * Created by qinyuan on 15-3-28.
 */
public class CategoryPosterDaoTest {
    private CategoryPosterDao dao = new CategoryPosterDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(dao.getInstances(1));
    }
}
