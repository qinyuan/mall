package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test ArticleDao
 * Created by qinyuan on 15-4-14.
 */
public class ArticleDaoTest {
    @Test
    public void testGetInstances() throws Exception {
        System.out.println(new ArticleDao().getInstances().size());
    }
}
