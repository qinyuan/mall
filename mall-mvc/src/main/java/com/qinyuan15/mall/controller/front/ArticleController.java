package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.Article;
import com.qinyuan15.mall.core.dao.ArticleDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Admin page
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class ArticleController extends ImageController {
    private ArticleDao dao = new ArticleDao();

    @RequestMapping("/article")
    public String edit(ModelMap model, @RequestParam(value = "id", required = false) Integer id) {
        if (isPositive(id)) {
            Article article = dao.getInstance(id);
            model.addAttribute("article", article);
            setTitle(article.getTitle());
        } else {
            setTitle("文章不存在");
        }

        addCss("article-common");
        return "article";
    }
}
