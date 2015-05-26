package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.branch.BranchGroupBuilder;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.BranchDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BranchController
 * Created by qinyuan on 15-2-14.
 */
@Controller
public class BranchController extends ImageController {
    @RequestMapping("/branch")
    public String index() {
        setTitle("品牌大全");
        addIEJs("list-snapshots-ie-patch");
        return "branch";
    }

    /**
     * Query branches.
     * If parentId parameter present then return sub branches of that branch,
     * else return root branches
     *
     * @param parentId parent branch id
     * @return branches
     */
    @ResponseBody
    @RequestMapping("/json/branch.json")
    public String query(@RequestParam(value = "parentId", required = false) Integer parentId,
                        @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        BranchDao dao = new BranchDao();
        if (isPositive(categoryId)) {
            return toJson(adjustBranches(dao.getInstancesByCategoryId(categoryId)));
        }

        if (isPositive(parentId)) {
            return toJson(adjustBranches(dao.getSubInstances(parentId)));
        } else {
            return toJson(adjustBranches(dao.getRootInstances()));
        }
    }

    @ResponseBody
    @RequestMapping("/json/partialBranchGroupsIgnoreEmpty.json")
    public String queryPartialBranchGroupsIgnoreEmpty(@RequestParam(value = "parentId", required = false) Integer parentId) {
        List<Branch> branches = getBranchesByParentId(parentId);
        return branches.size() == 0 ? "[]" : toJson(adjustBranchGroups(
                new BranchGroupBuilder().setGroupSize(5).build(branches)));
    }

    @ResponseBody
    @RequestMapping("/json/partialBranchGroups.json")
    public String queryPartialBranchGroups(@RequestParam(value = "parentId", required = false) Integer parentId) {
        return toJson(adjustBranchGroups(new BranchGroupBuilder()
                .setGroupSize(5).build(getBranchesByParentId(parentId))));
    }

    private List<Branch> getBranchesByParentId(Integer parentId) {
        BranchDao dao = new BranchDao();
        return isPositive(parentId) ? dao.getSubInstances(parentId) : dao.getRootInstances();
    }

    @ResponseBody
    @RequestMapping("/json/branchGroups.json")
    public String queryBranchGroups() {
        return toJson(adjustBranchGroups(new BranchGroupBuilder().build()));
    }

    @ResponseBody
    @RequestMapping("/json/parentBranch.json")
    public String queryParent(@RequestParam(value = "branchId", required = true) Integer branchId) {
        Branch branch = HibernateUtils.get(Branch.class, branchId);
        if (branch == null) {
            return createParentResult(branchId, null, null);
        }

        Integer parentBranchId = branch.getParentId();
        Branch parentBranch = HibernateUtils.get(Branch.class, parentBranchId);
        if (parentBranch == null) {
            return createParentResult(branchId, null, null);
        }

        Integer grandBranchId = parentBranch.getParentId();
        Branch grandBranch = HibernateUtils.get(Branch.class, parentBranch.getParentId());
        if (grandBranch == null) {
            return createParentResult(parentBranchId, branchId, null);
        }

        return createParentResult(grandBranchId, parentBranchId, branchId);
    }

    private String createParentResult(Integer branchId, Integer subBranch1Id, Integer subBranch2Id) {
        Map<String, Integer> result = new HashMap<>();
        result.put("branchId", branchId);
        result.put("subBranch1Id", subBranch1Id);
        result.put("subBranch2Id", subBranch2Id);
        return toJson(result);
    }
}
