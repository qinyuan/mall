package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.commodity.CommodityBranchGroupBuilder;
import com.qinyuan15.mall.core.commodity.CommodityCategoryGroupBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Page to show add statistics
 * Created by qinyuan on 15-4-30.
 */
@Controller
public class AdminAddStatisticsController extends ImageController {
    @RequestMapping("/admin-add-statistics")
    public String index(ModelMap model,
                        @RequestParam(value = "statisticType", required = false) Integer statisticType,
                        @RequestParam(value = "orderType", required = false) Integer orderType) {

        if (isPositive(statisticType) && statisticType == 1) {
            CommodityCategoryGroupBuilder builder = new CommodityCategoryGroupBuilder();
            if (isPositive(orderType) && orderType == 1) {
                builder.orderRanking();
            } else {
                builder.orderByCommodityCount();
            }
            model.addAttribute("commodityCategoryGroups", builder.build());
        } else {
            CommodityBranchGroupBuilder builder = new CommodityBranchGroupBuilder();
            if (isPositive(orderType) && orderType == 1) {
                builder.orderByLetter();
            } else {
                builder.orderByCommodityCount();
            }
            model.addAttribute("commodityBranchGroups", adjustCommodityBranchGroups(builder.build()));
        }

        addCssAndJs("admin-normal-edit-page");
        addHeadJs("image-adjust");
        setTitle("录入统计");
        return "admin-add-statistics";
    }
}
