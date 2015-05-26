package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.mall.core.dao.HotSearchWordDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Controller to edit hot search word
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminHotSearchWordController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "/admin-hot-search-word-add-update", method = RequestMethod.POST)
    public Map<String, Object> addUpdate(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "content", required = true) String content,
                                         @RequestParam(value = "hot", required = false) Boolean hot,
                                         @RequestParam(value = "categoryId", required = true) Integer categoryId) {
        hot = (hot != null);

        HotSearchWordDao dao = new HotSearchWordDao();
        if (isPositive(id)) {
            dao.update(id, content, categoryId, hot);
            logAction("为'%s'分类更新搜索关键词'%s'", new CategoryDao().getNameById(categoryId),
                    content);
        } else {
            dao.add(content, categoryId, hot);
            logAction("为'%s'分类添加搜索关键词'%s'", new CategoryDao().getNameById(categoryId),
                    content);
        }

        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-hot-search-word-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            HotSearchWordDao dao = new HotSearchWordDao();
            logAction("删除搜索关键词'%s'", dao.getInstance(id).getContent());
            dao.delete(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-hot-search-word-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            HotSearchWordDao dao = new HotSearchWordDao();
            logAction("上移搜索关键词'%s'的排序", dao.getInstance(id).getContent());
            dao.rankUp(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-hot-search-word-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            HotSearchWordDao dao = new HotSearchWordDao();
            logAction("下移搜索关键词'%s'的排序", dao.getInstance(id).getContent());
            dao.rankDown(id);
        }
        return SUCCESS;
    }
}
