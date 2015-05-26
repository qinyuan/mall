package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test IndexLogoDao
 * Created by qinyuan on 15-3-19.
 */
public class IndexLogoDaoTest {
    private IndexLogoDao dao = new IndexLogoDao();

    @Test
    public void testGetInstances() {
        System.out.println(dao.getInstances().size());
    }

    @Test
    public void testRankUp() {
        //dao.rankUp(5);
        //dao.rankUp(6);
    }

    @Test
    public void testRankDown(){
        //dao.rankDown(5);
        //dao.rankDown(6);
    }

    @Test
    public void testAdd() {
        //dao.add("hello", "world");
    }
}
