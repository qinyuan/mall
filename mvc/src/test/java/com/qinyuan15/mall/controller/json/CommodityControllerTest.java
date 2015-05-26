package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test CommodityController
 * Created by qinyuan on 15-1-14.
 */
public class CommodityControllerTest {

    private CommodityController controller = new CommodityController();

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.injectRequest(controller);
        ControllerTestUtils.injectImageDownloader(controller);
        ControllerTestUtils.injectPictureUrlConvertor(controller);
    }

    @Test
    public void testGet() throws Exception {
        System.out.println(controller.get(null, null));
    }

    @Test
    public void testAdd() throws Exception {
        //java.util.Map<String, Object> result = controller.add("hello", "http://www.baidu.com", 10.0, "2012-01-01");
        //System.out.println(result);
    }
}
