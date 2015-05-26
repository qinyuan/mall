package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import java.util.List;

/**
 * Test HotSearchWordDao
 * Created by qinyuan on 15-2-28.
 */
public class HotSearchWordDaoTest {
    private HotSearchWordDao dao = new HotSearchWordDao();

    @Test
    public void testGetInstances() {
        List<HotSearchWord> hotSearchWords = dao.getInstances(5);
        System.out.println(hotSearchWords.size());
    }

    @Test
    public void testAdd() {
        //dao.add("helloWorld", 5, true);
        //dao.add("helloWorldAgain", 5, true);
    }

    @Test
    public void testUpdate() {
        //dao.update(1, "helloWorld", 5, false);
    }

    @Test
    public void testRankUp() {
        //dao.rankUp(2);
    }

    @Test
    public void testRankDown() {
        //dao.rankDown(2);
    }
}
