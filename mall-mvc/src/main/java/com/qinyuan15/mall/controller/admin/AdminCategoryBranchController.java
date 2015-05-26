package com.qinyuan15.mall.controller.admin;

import com.google.common.collect.Lists;
import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Controller to edit category branch relation
 * Created by qinyuan on 15-3-25.
 */
@Controller
public class AdminCategoryBranchController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "/admin-category-branch-add", method = RequestMethod.POST)
    public Map<String, Object> addUpdate(@RequestParam(value = "categoryId", required = true) Integer categoryId,
                                         @RequestParam(value = "branchIds", required = true) Integer[] branchIds) {
        if (isPositive(categoryId) && branchIds != null) {
            Category category = new CategoryDao().getInstance(categoryId);
            BranchDao branchDao = new BranchDao();
            String branchNames = "";
            for (Integer branchId : branchIds) {
                Branch branch = branchDao.getInstance(branchId);
                if (branchNames.length() > 0) {
                    branchNames += ",";
                }
                branchNames += "'" + branch.getName() + "'";
            }
            logAction("为'%s'分类添加了品牌'%s'", category.getName(), branchNames);

            new CategoryBranchDao().add(categoryId, Lists.newArrayList(branchIds));
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-branch-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "categoryId", required = true) Integer categoryId,
                                      @RequestParam(value = "branchId", required = true) Integer branchId) {
        if (isPositive(categoryId) && isPositive(branchId)) {
            CategoryBranchDao dao = new CategoryBranchDao();
            CategoryBranch categoryBranch = dao.getInstance(categoryId, branchId);
            logAction("删除'%s'分类中的'%s'品牌", categoryBranch.getCategoryName(),
                    categoryBranch.getBranchName());
            dao.delete(categoryBranch.getId());
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-branch-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "branchId", required = true) Integer branchId,
                                      @RequestParam(value = "categoryId", required = true) Integer categoryId) {
        if (isPositive(branchId) && isPositive(categoryId)) {
            CategoryBranchDao dao = new CategoryBranchDao();
            CategoryBranch categoryBranch = dao.getInstance(categoryId, branchId);
            logAction("上移'%s'分类中'%s'品牌的排序", categoryBranch.getCategoryName(),
                    categoryBranch.getBranchName());
            dao.rankUp(categoryBranch.getId());
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-branch-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "branchId", required = true) Integer branchId,
                                        @RequestParam(value = "categoryId", required = true) Integer categoryId) {
        if (isPositive(branchId) && isPositive(categoryId)) {
            CategoryBranchDao dao = new CategoryBranchDao();
            CategoryBranch categoryBranch = dao.getInstance(categoryId, branchId);
            logAction("下移'%s'分类中'%s'品牌的排序", categoryBranch.getCategoryName(),
                    categoryBranch.getBranchName());
            dao.rankDown(categoryBranch.getId());
        }
        return SUCCESS;
    }
}
