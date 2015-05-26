package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.branch.BranchGroup;
import com.qinyuan15.mall.core.branch.BranchGroupBuilder;
import com.qinyuan15.mall.core.commodity.CommoditySimpleSnapshot;
import com.qinyuan15.mall.core.commodity.CommoditySimpleSnapshotBuilder;
import com.qinyuan15.mall.core.dao.*;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import com.qinyuan15.utils.mvc.UrlBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin page
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminController extends ImageController {

    private final static String INDEX = "admin";
    private CommodityDao commodityDao = new CommodityDao();

    @RequestMapping("/admin")
    public String index(ModelMap model,
                        @RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "branchId", required = false) Integer branchId,
                        @RequestParam(value = "keyWord", required = false) String keyWord) {
        CommodityDao.Factory factory = CommodityDao.factory().setKeyWord(keyWord)
                .setCategoryId(categoryId).setBranchId(branchId);

        // security
        if (!securitySearcher.isSupperAdmin()) {
            Integer userId = securitySearcher.getUserId();
            factory.setUserId(userId);
        }

        AppConfig appConfig = getAppConfig();
        new PaginationAttributeAdder(factory, request)
                .setPageSize(appConfig.getAdminPaginationCommoditySize())
                .setVisibleButtonSize(appConfig.getAdminPaginationButtonSize())
                .setRowItemCountName("commodityCount")
                .add();

        @SuppressWarnings("unchecked")
        List<Commodity> commodities = (List) request.getAttribute(
                PaginationAttributeAdder.DEFAULT_ROW_ITEMS_NAME);
        List<CommoditySimpleSnapshot> snapshots = new CommoditySimpleSnapshotBuilder().build(
                commodities, pictureUrlConverter);
        model.addAttribute("commodities", snapshots);
        model.addAttribute("keyWord", keyWord);

        // category
        model.addAttribute("categories", createCategoryOptions(branchId, keyWord));
        if (isPositive(categoryId)) {
            Category category = new CategoryDao().getInstance(categoryId);
            if (category != null) {
                model.addAttribute("selectedCategoryName", category.getName());
                model.addAttribute("selectedCategoryId", categoryId);
            }
        }

        // branch
        List<BranchGroup> branchGroups = adjustBranchGroups(
                new BranchGroupBuilder().setGroupSize(5).build());
        model.addAttribute("branchGroups", branchGroups);
        if (isPositive(branchId)) {
            Branch branch = new BranchDao().getInstance(branchId);
            if (branch != null) {
                model.addAttribute("selectedBranchName", branch.getName());
                model.addAttribute("selectedBranchId", branchId);
            }
        }

        addCssAndJs("admin-branch-select-form");
        addIEJs("admin-ie-patch");
        setTitle("商品管理");

        return "admin";
    }

    private String redirectIndex() {
        String url = new UrlBuilder().setBaseUrl(INDEX)
                .addParam("branchId", getParameter("branchId"))
                .addParam("categoryId", getParameter("categoryId"))
                .addParam("keyWord", getParameter("keyWord"))
                .build();
        return redirect(url);
    }

    @RequestMapping("/admin-commodity-delete")
    public String delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("彻底删除商品'%s'", commodityDao.getNameById(id));
            commodityDao.delete(id);
        }
        return redirectIndex();
    }

    @RequestMapping("/admin-commodity-activate")
    public String activate(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("激活商品'%s'", commodityDao.getNameById(id));
            commodityDao.activate(id);
        }
        return redirectIndex();
    }

    @RequestMapping("/admin-commodity-deactivate")
    public String deactivate(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("删除商品'%s'", commodityDao.getNameById(id));
            commodityDao.deactivateByUser(id);
        }
        return redirectIndex();
    }

    private List<CategoryOption> createCategoryOptions(Integer branchId, String keyWord) {
        UrlBuilder urlBuilder = new UrlBuilder().setBaseUrl("admin")
                .addParam("branchId", branchId).addParam("keyWord", keyWord);


        List<CategoryOption> categoryOptions = new ArrayList<>();
        categoryOptions.add(new CategoryOption("(所有分类)",
                urlBuilder.addParam("categoryId", 0).build()));

        for (Category category : new CategoryDao().getInstances()) {
            String name = category.getName();
            if (isPositive(category.getParentId())) {
                name = "&nbsp;&nbsp;&nbsp;&nbsp;" + name;
            }
            String href = urlBuilder.addParam("categoryId", category.getId()).build();
            categoryOptions.add(new CategoryOption(name, href));
        }

        return categoryOptions;
    }

    public static class CategoryOption {
        private String name;
        private String href;

        CategoryOption(String name, String href) {
            this.name = name;
            this.href = href;
        }

        public String getName() {
            return name;
        }

        public String getHref() {
            return href;
        }
    }
}
