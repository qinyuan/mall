package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Real time crawler about commodity
 * Created by qinyuan on 14-12-27.
 */
@Controller
public class CommodityCrawlerController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityCrawlerController.class);

    @Autowired
    private CommodityPageParserBuilder pageParserBuilder;

    @ResponseBody
    @RequestMapping("/commodities-crawler.json")
    public String crawlCommodities(@RequestParam(value = "showIds", required = true) String[] showIds,
                                   @RequestParam(value = "crawlerUrls", required = true) String[] urls) {
        try {
            return toJson(getCrawler().get(showIds, urls));
        } catch (Exception e) {
            LOGGER.error("Error in parsing {}, info: {}", urls, e);
            return toJson(createFailResult("未知错误"));
        }
    }

    @ResponseBody
    @RequestMapping("/commodity-crawler.json")
    public String crawlCommodity(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam(value = "showId", required = true) String showId,
                                 @RequestParam(value = "crawlerUrl", required = true) String url) {
        try {
            return toJson(getCrawler().get(id, showId, url));
        } catch (Exception e) {
            LOGGER.error("Error in parsing {}, info: {}", url, e);
            return toJson(createFailResult("未知错误"));
        }
    }

    private FrontendCommodityCrawler getCrawler() {
        return new FrontendCommodityCrawler(getAppConfig(), pageParserBuilder);
    }
}
