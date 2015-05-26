package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.BranchDao;
import com.qinyuan15.mall.core.dao.Shoppe;
import com.qinyuan15.mall.core.dao.ShoppeDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminBranchController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminBranchController.class);
    private final static String SAVE_PATH_PREFIX = "mall/branch/logo/";
    private final static String INDEX_PAGE = "admin-branch";

    @RequestMapping("/admin-branch")
    public String index(ModelMap model) {
        List<Branch> branches = new BranchDao().getInstances();
        model.addAttribute("branches", adjustBranches(branches));
        addCssAndJs("admin-normal-edit-page");
        addHeadJs("image-adjust");
        setTitle("编辑品牌");
        return INDEX_PAGE;
    }

    @RequestMapping(value = "/admin-branch-add-update", method = RequestMethod.POST)
    public String addUpdate(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "name", required = true) String name,
                            @RequestParam(value = "logo", required = true) String logo,
                            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                            @RequestParam(value = "squareLogo", required = true) String squareLogo,
                            @RequestParam(value = "squareLogoFile", required = false) MultipartFile squareLogoFile,
                            @RequestParam(value = "poster", required = true) String poster,
                            @RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
                            @RequestParam(value = "parentId", required = false) Integer parentId,
                            @RequestParam(value = "firstLetter", required = true) String firstLetter,
                            @RequestParam(value = "slogan", required = true) String slogan,
                            @RequestParam(value = "shoppeNames", required = false) String[] shoppeNames,
                            @RequestParam(value = "shoppeUrls", required = false) String[] shoppeUrls) {

        if (!isPositive(parentId)) {
            parentId = null;
        }

        // deal with logUrl
        String logoUrl;
        try {
            logoUrl = getSavePath(logo, logoFile, SAVE_PATH_PREFIX);
        } catch (Exception e) {
            LOGGER.error("fail to deal with logoUrl, logo:{}, logoFile:{}, error:{}"
                    , logo, logoFile, e);
            return toIndexPage("矩形Logo文件处理失败!");
        }

        // deal with squareLogoUrl
        String squareLogoUrl;
        try {
            squareLogoUrl = getSavePath(squareLogo, squareLogoFile, SAVE_PATH_PREFIX);
        } catch (Exception e) {
            LOGGER.error("fail to deal with squareLogoUrl, squareLogo:{}, squareLogoFile:{}, error:{}"
                    , logo, logoFile, e);
            return toIndexPage("方形Logo文件处理失败!");
        }

        // deal with poster
        String posterUrl;
        try {
            if (!StringUtils.hasText(poster) && !validateUploadFile(posterFile)) {
                posterUrl = null;
            } else {
                posterUrl = getSavePath(poster, posterFile, SAVE_PATH_PREFIX);
            }
        } catch (Exception e) {
            LOGGER.error("fail to deal with posterUrl, poster:{}, posterFile:{}, error:{}"
                    , poster, posterFile, e);
            return toIndexPage("海报文件处理失败!");
        }

        // build branch object
        Branch branch = isPositive(id) ? new BranchDao().getInstance(id) : new Branch();
        branch.setName(name);
        branch.setLogo(logoUrl);
        branch.setSquareLogo(squareLogoUrl);
        branch.setPoster(posterUrl);
        branch.setParentId(parentId);
        branch.setSlogan(slogan);
        if (StringUtils.hasText(firstLetter)) {
            branch.setFirstLetter(firstLetter.substring(0, 1));
        }

        // save or update branch
        if (isPositive(id)) {
            HibernateUtils.update(branch);
            logAction("更新品牌'%s'", branch.getName());
        } else {
            id = HibernateUtils.save(branch);
            logAction("添加品牌'%s'", branch.getName());
        }

        ShoppeDao dao = new ShoppeDao();
        dao.clear(id);
        dao.save(createShoppes(id, shoppeNames, shoppeUrls));
        return toIndexPage(null);
    }

    private String toIndexPage(String errorInfo) {
        if (StringUtils.hasText(errorInfo)) {
            return redirect(addErrorInfoParameter(INDEX_PAGE, errorInfo));
        } else {
            return redirect(INDEX_PAGE);
        }
    }

    private List<Shoppe> createShoppes(Integer branchId, String[] shoppeNames, String[] shoppeUrls) {
        if (shoppeNames == null || shoppeUrls == null) {
            return new ArrayList<>();
        }
        int size = Math.min(shoppeNames.length, shoppeUrls.length);
        List<Shoppe> shoppes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Shoppe shoppe = new Shoppe();
            shoppe.setBranchId(branchId);
            shoppe.setName(shoppeNames[i]);
            shoppe.setUrl(shoppeUrls[i]);
            shoppes.add(shoppe);
        }
        return shoppes;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-branch-deletable", method = RequestMethod.POST)
    public Map<String, Object> deletable(@RequestParam(value = "id", required = true) Integer id) {
        BranchDao dao = new BranchDao();
        boolean hasSubInstance = dao.hasSubInstance(id);
        boolean hasCommodity = dao.hasCommodity(id);

        if (hasSubInstance) {
            if (hasCommodity) {
                return createFailResult("该品牌被某些商品使用且存在子品牌,确定删除?");
            } else {
                return createFailResult("该品牌存在子品牌,确定删除?");
            }
        } else {
            if (hasCommodity) {
                return createFailResult("该品牌被某些商品使用,确定删除?");
            } else {
                return SUCCESS;
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/admin-branch-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        BranchDao dao = new BranchDao();
        logAction("删除品牌'%s'", dao.getInstance(id).getName());
        dao.delete(id);
        return SUCCESS;
    }
}
