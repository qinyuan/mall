package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test CommodityPictureDao
 * Created by qinyuan on 15-1-15.
 */
public class CommodityPictureDaoTest {
    private CommodityPictureDao dao = new CommodityPictureDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(dao.getInstances(1));
    }

    @Test
    public void testDeleteInstances() throws Exception {
        //dao.deleteDetailInstances(10);
    }

    @Test
    public void testGetFirstInstance() throws Exception {
        CommodityPicture result = dao.getFirstInstance(11);
        System.out.println(result);
        if (result !=null) {
            System.out.println(result.getUrl());
        }
    }
}
