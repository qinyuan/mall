package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.PersistObjectUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test PersistObjectUtils
 * Created by qinyuan on 15-3-13.
 */
public class PersistObjectUtilsTest {
    @Test
    public void testGetIds() throws Exception {
        List<Category> categories = mockCategories();
        List<Integer> ids = PersistObjectUtils.getIds(categories);
        assertThat(ids).containsExactly(1, 2, 3, 4, 5);
    }

    private List<Category> mockCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(mockCategory(1));
        categories.add(mockCategory(2));
        categories.add(mockCategory(3));
        categories.add(mockCategory(4));
        categories.add(mockCategory(5));
        return categories;
    }

    private Category mockCategory(int id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Test
    public void testGetIdsString() throws Exception {
        List<Category> categories = mockCategories();
        String idsString = PersistObjectUtils.getIdsString(categories);
        assertThat(idsString).isEqualTo("1,2,3,4,5");
    }
}
