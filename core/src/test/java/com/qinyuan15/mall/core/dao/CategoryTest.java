package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.junit.Test;

/**
 * Test Category
 * Created by qinyuan on 15-2-25.
 */
public class CategoryTest {
    @Test
    public void test() throws Exception {
        System.out.println(HibernateUtils.getList(Category.class).size());
    }
}
