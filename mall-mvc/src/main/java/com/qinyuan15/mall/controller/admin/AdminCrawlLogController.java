package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminCrawlLogController extends ImageController {
    @RequestMapping("/admin-crawl-log")
    public String index(ModelMap model,
                        @RequestParam(value = "commodityId", required = false) Integer commodityId,
                        @RequestParam(value = "success", required = false) Integer success,
                        @RequestParam(value = "showId", required = false) String showId) {
        Boolean successValue;
        if (success == null) {
            successValue = null;
        } else if (success.equals(1)) {
            successValue = true;
        } else if (success.equals(2)) {
            successValue = false;
        } else {
            successValue = null;
        }

        CommodityCrawlLogDao.Factory factory = CommodityCrawlLogDao.factory()
                .setCommodityId(commodityId).setSuccess(successValue).setCommodityShowId(showId);
        new PaginationAttributeAdder(factory, request).setRowItemsName("crawlLogs").add();

        CommodityDao commodityDao = new CommodityDao();
        model.addAttribute("commodityCount", commodityDao.getCount());
        model.addAttribute("activeCommodityCount", commodityDao.getActiveCount());
        model.addAttribute("crawlCommodityCount", commodityDao.getCrawlCount());
        model.addAttribute("withParametersCommodityCount", commodityDao.getWithParametersCount());

        addCssAndJs("admin-normal-edit-page");
        setTitle("爬虫日志");
        return "admin-crawl-log";
    }
}
