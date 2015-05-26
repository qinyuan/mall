package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.junit.Test;


/**
 * Test Shoppe
 * Created by qinyuan on 15-3-1.
 */
public class ShoppeTest {
    @Test
    public void test() {
        System.out.println(HibernateUtils.getList(Shoppe.class).size());
    }
}
