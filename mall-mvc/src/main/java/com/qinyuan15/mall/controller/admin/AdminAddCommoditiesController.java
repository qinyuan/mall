package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.core.dao.Commodity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Page to edit commodity
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminAddCommoditiesController extends CommodityAddController {

    private final static String PAGE_URL = "admin-add-commodities";

    @RequestMapping("/admin-add-commodities")
    public String index() {
        setTitle("批量添加商品");

        addJs("commodity-parameters");
        addCssAndJs("admin-commodity-edit-form");
        addCssAndJs("admin-branch-select-form");
        return PAGE_URL;
    }

    @RequestMapping(value = "/admin-add-commodities-submit", method = RequestMethod.POST)
    public String submit(@RequestParam(value = "showId", required = true) String[] showId,
                         @RequestParam(value = "url", required = true) String[] url,
                         @RequestParam(value = "buyUrl", required = true) String[] buyUrl,
                         @RequestParam(value = "commodityName", required = true) String[] commodityName,
                         /*@RequestParam(value = "parameters", required = true) String[] parameters,*/
                         @RequestParam(value = "imageUrls", required = false) String[] imageUrls,
                         @RequestParam(value = "detailImageUrls", required = false) String[] detailImageUrls,
                         @RequestParam(value = "branchId", required = true) Integer branchId,
                         @RequestParam(value = "subBranch1Id", required = true) Integer subBranch1Id,
                         @RequestParam(value = "subBranch2Id", required = true) Integer subBranch2Id,
                         @RequestParam(value = "categoryId", required = true) Integer categoryId,
                         @RequestParam(value = "subCategoryId", required = true) Integer subCategoryId) {

        if (showId == null || showId.length == 0 ||
                url == null || url.length == 0 ||
                buyUrl == null || buyUrl.length == 0 ||
                commodityName == null || commodityName.length == 0) {
            return redirect(PAGE_URL);
        }

        if (imageUrls == null) {
            imageUrls = new String[0];
        }
        if (detailImageUrls == null) {
            detailImageUrls = new String[0];
        }

        if (!isPositive(branchId)) {
            return toEditPage("品牌未设置");
        }
        Integer finalBranchId = getBranchId(branchId, subBranch1Id, subBranch2Id);

        if (!isPositive(categoryId)) {
            return toEditPage("商品分类未设置");
        }
        Integer finalCategoryId = getCategoryId(categoryId, subCategoryId);

        int commodityCount = min(showId.length, url.length, buyUrl.length,
                commodityName.length);
        for (int i = 0; i < commodityCount; i++) {
            addCommodity(finalBranchId, finalCategoryId, showId[i], url[i], buyUrl[i],
                    commodityName[i], "" /*parameters[i]*/, imageUrls, detailImageUrls);
        }

        return redirect(PAGE_URL);
    }

    private int min(int... values) {
        int minValue = Integer.MAX_VALUE;
        for (int value : values) {
            if (minValue > value) {
                minValue = value;
            }
        }
        return minValue;
    }

    private void addCommodity(Integer branchId, Integer categoryId, String showId,
                              String url, String buyUrl, String commodityName,
                              String parameters, String[] imageUrls, String[] detailImageUrls) {
        Commodity commodity = newCommodity();
        commodity.setBranchId(branchId);
        commodity.setCategoryId(categoryId);
        commodity.setShowId(showId);
        commodity.setUrl(url);
        commodity.setBuyUrl(buyUrl);
        commodity.setName(commodityName);
        commodity.setParameters(parameters);

        Integer id;
        try {
            id = addCommodity(commodity);
        } catch (Exception e) {
            return;
        }

        try {
            addImages(id, fetchImageUrls(showId, imageUrls));
        } catch (Exception e) {
            // ignore exception
        }

        try {
            addDetailImages(id, fetchImageUrls(showId, detailImageUrls));
        } catch (Exception e) {
            // ignore exception
        }
    }

    private List<String> fetchImageUrls(String showId, String[] imageUrls) {
        if (imageUrls == null) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (String url : imageUrls) {
            if (StringUtils.hasText(url) && url.startsWith(showId + "_")) {
                list.add(url.substring(showId.length() + 1));
            }
        }
        return list;
    }

    private String toEditPage(String errorInfo) {
        return redirect(addErrorInfoParameter(PAGE_URL, errorInfo));
    }
}
