package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.article.ArticleUtils;
import com.qinyuan15.mall.core.dao.ArticleDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Admin page
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminArticleController extends ImageController {
    private ArticleDao dao = new ArticleDao();

    @RequestMapping("/admin-article-list")
    public String list(ModelMap model) {
        model.addAttribute("articles", dao.getInstances());
        setTitle("文章列表");
        addCssAndJs("admin-normal-edit-page");
        return "admin-article-list";
    }

    @RequestMapping("/admin-article-edit")
    public String edit(ModelMap model, @RequestParam(value = "id", required = false) Integer id) {
        if (isPositive(id)) {
            model.addAttribute("article", dao.getInstance(id));
        }

        addCss("article-common");
        addJs("lib/ckeditor/ckeditor");
        addJs("lib/jscolor/jscolor");
        setTitle("编辑文章");
        return "admin-article-edit";
    }

    @RequestMapping(value = "/admin-article-add-update", method = RequestMethod.POST)
    public String addUpdate(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "content", required = true) String content,
                            @RequestParam(value = "backgroundColor", required = false) String backgroundColor) {

        if (isPositive(id)) {
            dao.update(id, content, backgroundColor);
            logAction("更新文章'%s'", ArticleUtils.getTitle(content));
        } else {
            dao.add(content, backgroundColor);
            logAction("添加文章'%s'", ArticleUtils.getTitle(content));
        }

        return redirect("admin-article-list.html");
    }


    @ResponseBody
    @RequestMapping(value = "/admin-article-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        logAction("删除文章'%s'", dao.getTitleById(id));
        dao.delete(id);
        return SUCCESS;
    }
}
