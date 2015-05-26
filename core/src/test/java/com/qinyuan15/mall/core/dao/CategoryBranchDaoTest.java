package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test CateogryBranchDao
 * Created by qinyuan on 15-3-25.
 */
public class CategoryBranchDaoTest {
    private CategoryBranchDao dao = new CategoryBranchDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(dao.getInstances(1).size());
        System.out.println(dao.getInstances(2).size());
    }

    @Test
    public void testAdd() throws Exception {
        //dao.add(1, 2);
        //dao.add(1, 3);
        //dao.add(2, 2);
    }

    @Test
    public void testDelete() throws Exception {
    }

    @Test
    public void testGetInstance() throws Exception {
        CategoryBranch categoryBranch = dao.getInstance(3);
        System.out.println(categoryBranch == null);
        if (categoryBranch != null) {
            System.out.println(categoryBranch.getBranchId());
            System.out.println(categoryBranch.getCategoryId());
        }
    }

    @Test
    public void testRankUp() throws Exception {
        //dao.rankUp(2);
    }

    @Test
    public void testRankDown() throws Exception {
        //dao.rankDown(2);
    }
}
