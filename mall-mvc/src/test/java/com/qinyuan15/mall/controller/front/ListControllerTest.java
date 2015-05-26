package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ListController
 * Created by qinyuan on 15-2-17.
 */
public class ListControllerTest {
    @Test
    public void testIndex() throws Exception {
        ListController controller = new ListController();
        ControllerTestUtils.injectRequest(controller);

        String result = controller.index(ControllerTestUtils.mockModelMap(), 1);
        assertThat(result).startsWith("redirect:list?keyWord=");
    }
}
