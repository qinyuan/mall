package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.Category;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller to query category
 * Created by qinyuan on 15-2-26.
 */
@Controller
public class CategoryController extends BaseController {
    /**
     * Query categories.
     * If parentId parameter present then return sub categories of that category,
     * else return root categories
     *
     * @param parentId parent category id
     * @return categories
     */
    @ResponseBody
    @RequestMapping("/json/category.json")
    public String query(@RequestParam(value = "parentId", required = false) Integer parentId) {
        CategoryDao dao = new CategoryDao();
        if (isPositive(parentId)) {
            return toJson(dao.getSubInstances(parentId));
        } else {
            return toJson(dao.getRootInstances());
        }
    }

    @ResponseBody
    @RequestMapping("/json/parentCategory.json")
    public String queryParent(@RequestParam(value = "categoryId", required = true) Integer categoryId) {
        Category category = HibernateUtils.get(Category.class, categoryId);
        if (category == null) {
            return createParentResult(categoryId, null);
        }

        Integer parentCategoryId = category.getParentId();
        Category parentCategory = HibernateUtils.get(Category.class, parentCategoryId);
        if (parentCategory == null) {
            return createParentResult(categoryId, null);
        }
        return createParentResult(parentCategoryId, categoryId);
    }

    private String createParentResult(Integer categoryId, Integer subCategoryId) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("categoryId", categoryId);
        result.put("subCategoryId", subCategoryId);
        return toJson(result);
    }
}
