package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test SeoKeywordDao
 * Created by qinyuan on 15-4-12.
 */
public class SeoKeywordDaoTest {
    @Test
    public void testGetInstances() throws Exception {
        System.out.println(new SeoKeywordDao().getInstances().size());
    }
}
