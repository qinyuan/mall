package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.SimpleCommodity;
import org.junit.Test;

/**
 * Test DatabaseCommodityPool
 * Created by qinyuan on 15-1-9.
 */
public class DatabaseCommodityPoolTest {
    @Test
    public void testNext() throws Exception {
        DatabaseCommodityPool pool = new DatabaseCommodityPool();
        for (int i = 0; i < 100; i++) {
            SimpleCommodity commodity = pool.next();
            if (commodity != null) {
                System.out.println(i + " " + commodity.getId());
            }
        }
    }
}
