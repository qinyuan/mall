package com.qinyuan15.mall.core.category;

import org.junit.Test;

import java.util.List;

/**
 * Test HotSearchWordGroup
 * Created by qinyuan on 15-3-21.
 */
public class HotSearchWordGroupTest {
    @Test
    public void testGetInstances() throws Exception {
        List<RichCategory> richCategories = RichCategory.getInstances();
        System.out.println(richCategories.size());
    }
}
