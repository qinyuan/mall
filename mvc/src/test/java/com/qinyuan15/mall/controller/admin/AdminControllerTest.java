package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * Test AdminController
 * Created by qinyuan on 15-2-24.
 */
public class AdminControllerTest {
    @Test
    public void testIndex() throws Exception {
        AdminController controller = new AdminController();
        ControllerTestUtils.injectRequest(controller);
        ControllerTestUtils.injectImageDownloader(controller);

        ModelMap modelMap = ControllerTestUtils.mockModelMap();
        controller.index(modelMap, null, null, null);
        List commodities = (List) modelMap.get("commodities");
        System.out.println(commodities.size());
    }
}
