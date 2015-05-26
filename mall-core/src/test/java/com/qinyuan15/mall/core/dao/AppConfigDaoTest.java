package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test AppConfigDao
 * Created by qinyuan on 15-3-22.
 */
public class AppConfigDaoTest {
    private AppConfigDao dao = new AppConfigDao();

    @Test
    public void testGetInstance() throws Exception {
        AppConfig appConfig = dao.getInstance();
        System.out.println(appConfig.getId());
        System.out.println(appConfig.getDetailText());
        //System.out.println(appConfig.getDetailImages());
    }

    @Test
    public void testUpdate() throws Exception {
        /*
        AppConfig config = dao.getInstance();
        config.setDetailText("helloWorld");
        dao.update(config);
        */
    }
}
