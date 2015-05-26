package com.qinyuan15.mall.core.commodity;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test CommodityBranchGroup
 * Created by qinyuan on 15-4-30.
 */
public class CommodityBranchGroupBuilderTest {
    private CommodityBranchGroupBuilder builder;

    @Before
    public void setUp() {
        builder = new CommodityBranchGroupBuilder();
    }

    @Test
    public void testCreate() throws Exception {
        List<CommodityBranchGroup> groups = builder.build();
        for (CommodityBranchGroup group : groups) {
            System.out.println(group.getBranch().getName() + " " + group.getCommodityCount());
        }
    }

    @Test
    public void testCreate1() throws Exception {
        List<CommodityBranchGroup> groups = builder.orderByCommodityCount().build();
        for (CommodityBranchGroup group : groups) {
            System.out.println(group.getBranch().getName() + " " + group.getCommodityCount());
        }
    }
}
