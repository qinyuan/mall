package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Test;

/**
 * Test BranchController
 * Created by qinyuan on 15-2-23.
 */
public class BranchControllerTest {
    @Test
    public void testQuery() throws Exception {
        BranchController controller = new BranchController();
        ControllerTestUtils.injectRequest(controller);
        System.out.println(controller.query(null, null));
        System.out.println(controller.query(7, null));
    }
}
