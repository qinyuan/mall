package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AppraiseGroup
 * Created by qinyuan on 15-2-24.
 */
public class AppraiseGroupTest {
    @Test
    public void test() {
        @SuppressWarnings("unchecked")
        List<AppraiseGroup> list = HibernateUtils.getList("AppraiseGroup");
        for (AppraiseGroup appraiseGroup : list) {
            assertThat(appraiseGroup).isNotNull();
        }
    }
}
