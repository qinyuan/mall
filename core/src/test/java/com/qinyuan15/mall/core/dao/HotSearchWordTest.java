package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test HotSearchWord
 * Created by qinyuan on 15-2-28.
 */
public class HotSearchWordTest {

    @Test
    public void test() throws Exception {
        for (HotSearchWord hotSearchWord : HibernateUtils.getList(HotSearchWord.class)) {
            assertThat(hotSearchWord.getId()).isGreaterThan(0);
        }
    }
}
