package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.CategoryPoster;
import com.qinyuan15.mall.core.dao.CategoryPosterDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller to query category poster
 * Created by qinyuan on 15-2-27.
 */
@Controller
public class CategoryPosterController extends ImageController {

    @ResponseBody
    @RequestMapping("/json/category-poster.json")
    public String index(@RequestParam(value = "categoryId", required = true) Integer categoryId) {
        CategoryPosterDao dao = new CategoryPosterDao();
        List<CategoryPoster> posters = dao.getInstances(categoryId);
        adjustCategoryPosters(posters);
        return toJson(posters);
    }
}
