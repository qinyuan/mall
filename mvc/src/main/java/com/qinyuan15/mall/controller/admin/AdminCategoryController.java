package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.branch.BranchGroupBuilder;
import com.qinyuan15.mall.core.category.RichCategory;
import com.qinyuan15.mall.core.dao.Category;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Page to edit category
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminCategoryController extends ImageController {
    @RequestMapping("/admin-category")
    public String index(ModelMap model) {
        CategoryDao categoryDao = new CategoryDao();
        model.addAttribute("rootCategories", categoryDao.getRootInstances());

        List<RichCategory> richCategories = RichCategory.getInstances();
        for (RichCategory richCategory : richCategories) {
            adjustBranches(richCategory.getBranches());
            adjustCategoryPosters(richCategory.getPosters());
        }
        model.addAttribute("richCategories", richCategories);
        model.addAttribute("branchGroups", adjustBranchGroups(
                new BranchGroupBuilder().setGroupSize(5).build()));
        //model.addAttribute("branches", adjustBranches(new BranchDao().getInstances()));

        addCssAndJs("admin-normal-edit-page");
        addCssAndJs("admin-branch-select-form");
        addHeadJs("image-adjust");

        setTitle("编辑商品分类");
        return "admin-category";
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-add-update", method = RequestMethod.POST)
    public Map<String, Object> addUpdate(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "name", required = true) String name,
                                         @RequestParam(value = "parentId", required = false) Integer parentId) {
        if (!StringUtils.hasText(name)) {
            return createFailResult("名称不能为空！");
        }

        if (!isPositive(parentId)) {
            parentId = null;
        }

        CategoryDao dao = new CategoryDao();
        // save or update
        if (isPositive(id)) {
            Category category = new CategoryDao().getInstance(id);
            category.setName(name);
            category.setParentId(parentId);
            HibernateUtils.update(category);
            logAction("更新商品分类'%s'", category.getName());
        } else {
            dao.add(name, parentId);
            logAction("添加商品分类'%s'", name);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-deletable", method = RequestMethod.POST)
    public Map<String, Object> deletable(@RequestParam(value = "id", required = true) Integer id) {
        CategoryDao dao = new CategoryDao();
        boolean hasSubInstance = dao.hasSubInstance(id);
        boolean hasCommodity = dao.hasCommodity(id);

        if (hasSubInstance) {
            if (hasCommodity) {
                return createFailResult("该分类被某些商品使用且存在子分类,确定删除?");
            } else {
                return createFailResult("该分类存在子分类,确定删除?");
            }
        } else {
            if (hasCommodity) {
                return createFailResult("该分类被某些商品使用,确定删除?");
            } else {
                return SUCCESS;
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        CategoryDao dao = new CategoryDao();
        logAction("删除商品分类'%s'", dao.getNameById(id));
        dao.delete(id);
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            CategoryDao dao = new CategoryDao();
            logAction("上移'%s'分类的排序", dao.getNameById(id));
            dao.rankUp(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            CategoryDao dao = new CategoryDao();
            logAction("下移'%s'分类的排序", dao.getNameById(id));
            dao.rankDown(id);
        }
        return SUCCESS;
    }
}
