package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test CategoryDao
 * Created by qinyuan on 15-3-7.
 */
public class CategoryDaoTest {
    private CategoryDao dao = new CategoryDao();

    @Test
    public void testIsUsed() throws Exception {
        System.out.println(dao.isUsed(3));
        System.out.println(dao.isUsed(4));
        System.out.println(dao.isUsed(5));
        System.out.println(dao.isUsed(6));
    }

    @Test
    public void testGetSubInstances() {
        List<Category> categories = dao.getSubInstances(1);
        System.out.println(categories.size());
        for (Category category : categories) {
            assertThat(category.getParentId()).isEqualTo(1);
        }
    }

    @Test
    public void testGetSubInstancesAndSelf() {
        List<Category> categories = dao.getSubInstancesAndSelf(1);
        System.out.println(categories.size());
        for (Category category : categories) {
            assertThat(category.getId().equals(1) || category.getParentId().equals(1)).isTrue();
        }
    }
}
