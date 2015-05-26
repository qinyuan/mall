package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static com.qinyuan15.mall.controller.ControllerTestUtils.injectRequest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test CommodityCrawlerController
 * Created by qinyuan on 15-2-23.
 */
public class CommodityCrawlerControllerTest {
    private CommodityCrawlerController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CommodityCrawlerController();
        injectRequest(controller);
        Whitebox.getField(CommodityCrawlerController.class, "pageParserBuilder")
                .set(controller, new CommodityPageParserBuilder(null));
    }

    @Test
    public void testCrawlCommodities() throws Exception {
        String[] showIds = {"44202510076", "43823326756"};
        String[] urls = {
                "http://s.etao.com/detail/44202510076.html",
                "http://s.etao.com/detail/43823326756.html"
        };
        //String result = controller.crawlCommodities(showIds, urls);
        //System.out.println(result);
    }

    @Test
    public void testIndex() throws Exception {
        String result = controller.crawlCommodity(null, "40780735321",
                "http://s.etao.com/detail/40780735321.html");
        System.out.println(result);
        assertThat(result).contains("name").contains("buyUrl").contains("imageUrls")
                .contains("detailImageUrls");
    }

    @Test
    public void testIndex2() throws Exception {
        String result = controller.crawlCommodity(null, "40780735321",
                "http://s.etao.com/detail/407807353211.html");
        System.out.println(result);
        /*
        assertThat(result).contains("name").contains("buyUrl").contains("imageUrls")
                .contains("detailImageUrls");
                */
    }
}
