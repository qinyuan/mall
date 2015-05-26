package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AppraiseGroupDao
 * Created by qinyuan on 15-2-24.
 */
public class AppraiseGroupDaoTest {
    private AppraiseGroupDao dao = new AppraiseGroupDao();

    @Test
    public void testGetInstancesByCommodityId() throws Exception {
        assertThat(dao.getInstancesByCommodityId(1)).isNotNull();
    }

    @Test
    public void testGetInstancesByCommodityId1() throws Exception {
        assertThat(dao.getInstancesByCommodityId(10, true)).isNotNull();
        assertThat(dao.getInstancesByCommodityId(10, false)).isNotNull();
        System.out.println(dao.getInstancesByCommodityId(10, true));
    }

    @Test
    public void testSave() throws Exception {
        //dao.save(1, new String[]{"aaaaaa"}, false);
    }
}
