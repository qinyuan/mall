package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.config.LinkAdapter;
import com.qinyuan15.mall.core.dao.IndexLogo;
import com.qinyuan15.mall.core.dao.IndexLogoDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Page to edit logo of index
 * Created by qinyuan on 15-3-19.
 */
@Controller
public class AdminIndexLogoController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminIndexLogoController.class);
    private final static String SAVE_PATH_PREFIX = "mall/indexLogo/logo/";
    private final static String INDEX = "admin-index-logo";

    @RequestMapping("/admin-index-logo")
    public String index(ModelMap model) {
        model.addAttribute("indexLogos", adjustIndexLogos(new IndexLogoDao().getInstances()));
        addCssAndJs("admin-normal-edit-page");
        setTitle("编辑主页Logo");
        return INDEX;
    }

    @RequestMapping(value = "/admin-index-logo-add-update", method = RequestMethod.POST)
    public String addUpdate(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "logo", required = true) String logo,
                            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                            @RequestParam(value = "description", required = true) String description,
                            @RequestParam(value = "link", required = true) String link) {
        // deal with logUrl
        String logoUrl;
        try {
            logoUrl = getSavePath(logo, logoFile, SAVE_PATH_PREFIX);
        } catch (Exception e) {
            LOGGER.error("fail to deal with logoUrl, logo:{}, logoFile:{}, error:{}"
                    , logo, logoFile, e);
            return redirect(addErrorInfoParameter(INDEX, "图片文件处理失败!"));
        }

        // adjust link
        link = new LinkAdapter().adjust(link);

        IndexLogoDao dao = new IndexLogoDao();
        if (isPositive(id)) {
            IndexLogo indexLogo = dao.getInstance(id);
            indexLogo.setPath(logoUrl);
            indexLogo.setLink(link);
            indexLogo.setDescription(description);
            HibernateUtils.update(indexLogo);
            logAction("更新主页Logo'%s'", description);
        } else {
            dao.add(logoUrl, link, description);
            logAction("添加主页Logo'%s'", description);
        }
        return redirect(INDEX);
    }

    @ResponseBody
    @RequestMapping(value = "/admin-index-logo-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            IndexLogoDao dao = new IndexLogoDao();
            logAction("删除主页Logo'%s'", dao.getInstance(id).getDescription());
            dao.delete(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-index-logo-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            IndexLogoDao dao = new IndexLogoDao();
            logAction("上移主页Logo'%s'的排序", dao.getInstance(id).getDescription());
            dao.rankUp(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-index-logo-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            IndexLogoDao dao = new IndexLogoDao();
            logAction("下移主页Logo'%s'的排序", dao.getInstance(id).getDescription());
            dao.rankDown(id);
        }
        return SUCCESS;
    }
}
