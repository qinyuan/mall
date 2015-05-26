package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.core.dao.AppraiseGroupDao;
import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * Page to edit commodity
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminEditCommodityController extends CommodityAddController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminEditCommodityController.class);

    private final static String EDIT_PAGE = "admin-edit-commodity";

    @RequestMapping("/admin-edit-commodity")
    public String index(ModelMap model, @RequestParam(value = "id", required = false) Integer id) {
        if (isPositive(id)) {
            setTitle("编辑商品");
            model.addAttribute("commodity", getCommodity(id));
            AppraiseGroupDao appraiseGroupDao = new AppraiseGroupDao();
            model.addAttribute("positiveAppraiseGroups", appraiseGroupDao.getPositiveInstances(id));
            model.addAttribute("negativeAppraiseGroups", appraiseGroupDao.getNegativeInstances(id));
        } else {
            setTitle("添加商品");
            model.addAttribute("commodity", newCommodity());
        }

        addJs("commodity-parameters");
        addCssAndJs("admin-commodity-edit-form");
        addCssAndJs("admin-branch-select-form");
        return EDIT_PAGE;
    }

    @RequestMapping(value = "/admin-commodity-add-update", method = RequestMethod.POST)
    public String addUpdate(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "branchId", required = true) Integer branchId,
            @RequestParam(value = "subBranch1Id", required = true) Integer subBranch1Id,
            @RequestParam(value = "subBranch2Id", required = true) Integer subBranch2Id,
            @RequestParam(value = "categoryId", required = true) Integer categoryId,
            @RequestParam(value = "subCategoryId", required = true) Integer subCategoryId,
            @RequestParam(value = "commodityName", required = true) String commodityName,
            @RequestParam(value = "serialNumber", required = true) String serialNumber,
            @RequestParam(value = "showId", required = true) String showId,
            @RequestParam(value = "buyUrl", required = true) String buyUrl,
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "parameters", required = true) String parameters,
            @RequestParam(value = "positiveAppraiseGroups", required = false) String[] positiveAppraiseGroups,
            @RequestParam(value = "negativeAppraiseGroups", required = false) String[] negativeAppraiseGroups,
            @RequestParam(value = "imageUrls", required = false) String[] imageUrls,
            @RequestParam(value = "detailImageUrls", required = false) String[] detailImageUrls) {
        //debugParameters();
        Commodity commodity = isPositive(id) ? getCommodity(id) : new Commodity();

        if (!isPositive(branchId)) {
            return toEditPage("品牌未设置", id);
        }
        commodity.setBranchId(getBranchId(branchId, subBranch1Id, subBranch2Id));

        if (!isPositive(categoryId)) {
            return toEditPage("商品分类未设置", id);
        }
        commodity.setCategoryId(getCategoryId(categoryId, subCategoryId));

        if (!StringUtils.hasText(commodityName)) {
            return toEditPage("名称未设置", id);
        }
        commodity.setName(commodityName);

        if (!StringUtils.hasText(serialNumber)) {
            return toEditPage("商品编号未设置", id);
        }
        commodity.setSerialNumber(serialNumber);

        if (!StringUtils.hasText(showId)) {
            return toEditPage("商品ID未设置", id);
        }
        commodity.setShowId(showId);

        if (!StringUtils.hasText(buyUrl)) {
            return toEditPage("购买链接未设置", id);
        }
        commodity.setBuyUrl(buyUrl);

        if (!StringUtils.hasText(url)) {
            return toEditPage("爬虫链接未设置", id);
        }
        commodity.setUrl(url);

        commodity.setParameters(parameters);

        // add or update
        if (isPositive(id)) {
            if (!(securitySearcher.isSupperAdmin() ||
                    securitySearcher.getUserId().equals(commodity.getUserId()))) {
                LOGGER.error("forbid user {} to edit commodity {}",
                        securitySearcher.getUsername(), commodity.getName());
                return toEditPage("您无权修改此商品!", id);
            }

            try {
                HibernateUtils.update(commodity);
                logAction("更新商品'%s'", commodity.getName());
                LOGGER.info("Update Commodity {}", commodity.getId());
            } catch (Exception e) {
                logAction("更新商品'%s'失败", commodity.getName());
                LOGGER.error("fail to Update Commodity {}, info: {}", commodity.getId(), e);
                return toEditPage("更新商品失败", id);
            }
        } else {
            try {
                id = addCommodity(commodity);
            } catch (Exception e) {
                return toEditPage("添加商品失败", id);
            }
        }

        try {
            AppraiseGroupDao appraiseGroupDao = new AppraiseGroupDao();
            appraiseGroupDao.clearAndSave(id, positiveAppraiseGroups, true);
            appraiseGroupDao.clearAndSave(id, negativeAppraiseGroups, false);
        } catch (Exception e) {
            LOGGER.error("fail to add appraiseGroups, id:{}, positiveAppraiseGroups:{}, negativeAppraiseGroups:{}, info:{}",
                    id, Arrays.toString(positiveAppraiseGroups), Arrays.toString(negativeAppraiseGroups), e);
            return toEditPage("添加商品评价失败", id);
        }

        try {
            addImages(id, imageUrls);
        } catch (Exception e) {
            return toEditPage("保存商品图片失败", id);
        }
        try {
            addDetailImages(id, detailImageUrls);
        } catch (Exception e) {
            return toEditPage("保存商品描述图片失败", id);
        }

        return redirect(EDIT_PAGE + "?id=" + id);
    }

    private String toEditPage(String errorInfo, Integer id) {
        String url = redirect(addErrorInfoParameter(EDIT_PAGE, errorInfo));
        if (isPositive(id)) {
            url += "&id=" + id;
        }
        return url;
    }

    private Commodity getCommodity(int id) {
        return new CommodityDao().getInstance(id);
    }
}
