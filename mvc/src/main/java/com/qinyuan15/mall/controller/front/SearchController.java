package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Index page controller
 * Created by qinyuan on 15-2-16.
 */
@Controller
public class SearchController extends BaseController {

    @RequestMapping("/search")
    public String index(ModelMap model,
                        @RequestParam(value = "keyWord", required = true) String keyWord,
                        @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        addCssAndJs("list-snapshots");
        addCssAndJs("commodity-search-form");
        model.addAttribute("keyWord", keyWord);

        CategoryDao categoryDao = new CategoryDao();
        if (isPositive(categoryId)) {
            model.addAttribute("subCategories", categoryDao.getSubInstancesAndSelf(categoryId));
        } else {
            model.addAttribute("subCategories", categoryDao.getRootInstances());
        }

        long count = CommodityDao.factory().setKeyWord(keyWord).setCategoryId(categoryId).getCount();

        addIEJs("commodity-search-form-ie-patch");
        addIEJs("list-snapshots-ie-patch");

        if (count > 0) {
            setTitle(keyWord + " 相关商品");
            return "search";
        } else {
            setTitle("找不到相关商品");
            return "search-no-result";
        }
    }
}
