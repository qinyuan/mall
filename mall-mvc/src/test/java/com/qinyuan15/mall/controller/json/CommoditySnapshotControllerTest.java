package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ControllerTestUtils;
import org.junit.Test;

/**
 * Test CommoditySnapshotController
 * Created by qinyuan on 15-3-14.
 */
public class CommoditySnapshotControllerTest {
    @Test
    public void testIndex() throws Exception {
        CommoditySnapshotController controller = new CommoditySnapshotController();
        ControllerTestUtils.injectRequest(controller);
        ControllerTestUtils.injectImageDownloader(controller);
        ControllerTestUtils.injectSnapshotConfig(controller);
        ControllerTestUtils.injectPictureUrlConvertor(controller);

        System.out.println(controller.index(null, true, null, null, "price", "asc", null, 0));
    }
}
