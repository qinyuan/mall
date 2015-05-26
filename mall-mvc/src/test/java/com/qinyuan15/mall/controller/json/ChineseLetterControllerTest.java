package com.qinyuan15.mall.controller.json;

import org.junit.Test;

import static com.qinyuan15.mall.controller.ControllerTestUtils.injectRequest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ChineseLetterController
 * Created by qinyuan on 15-2-25.
 */
public class ChineseLetterControllerTest {
    @Test
    public void testIndex() throws Exception {
        ChineseLetterController controller = new ChineseLetterController();
        injectRequest(controller);
        String testString = "中国";
        assertThat(controller.index(testString)).contains("\"result\": \"Z\"");
    }
}
