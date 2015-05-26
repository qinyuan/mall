package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Test;
import org.springframework.ui.ModelMap;

/**
 * Test AdminBranchController
 * Created by qinyuan on 15-2-21.
 */
public class AdminBranchControllerTest {
    @Test
    public void testIndex() throws Exception {
        AdminBranchController controller = new AdminBranchController();
        ModelMap modelMap = ControllerTestUtils.mockModelMap();
        for (int i = 0; i < 100; i++) {
            controller.index(modelMap);
        }
    }
}
