package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.config.LinkAdapter;
import com.qinyuan15.mall.core.dao.AppConfig;
import com.qinyuan15.mall.core.dao.AppConfigDao;
import com.qinyuan15.mall.core.dao.AppConfigDetailImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Page to edit category
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminDetailController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDetailController.class);
    private final static String ADMIN_DETAIL = "admin-detail";
    private final static String SAVE_PATH_PREFIX = "mall/detail/images/";

    @RequestMapping("/admin-detail")
    public String index() {
        addCssAndJs("admin-normal-edit-page");
        addJs("lib/ckeditor/ckeditor");

        setTitle("编辑详细页面");
        return ADMIN_DETAIL;
    }

    @RequestMapping(value = "/admin-detail-update", method = RequestMethod.POST)
    public String update(@RequestParam(value = "detailText", required = true) String detailText,
                         @RequestParam(value = "relatedCommoditySize", required = true) Integer relatedCommoditySize) {
        AppConfigDao dao = new AppConfigDao();
        AppConfig appConfig = dao.getInstance();

        if (isDifferent(appConfig.getDetailText(), detailText)) {
            appConfig.setDetailText(detailText);
            logAction("更改了商品详情页的文字描述内容");
        }

        if (isDifferent(appConfig.getRelatedCommoditySize(), relatedCommoditySize)) {
            appConfig.setRelatedCommoditySize(relatedCommoditySize);
            logAction("将商品详情页的相关商品数量限制修改为'%s'", relatedCommoditySize);
        }

        dao.update(appConfig);
        return redirect(ADMIN_DETAIL);
    }

    @RequestMapping(value = "/admin-detail-image-add-update", method = RequestMethod.POST)
    public String addUpdateImage(@RequestParam(value = "id", required = true) Integer id,
                                 @RequestParam(value = "image", required = true) String image,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                 @RequestParam(value = "link", required = true) String link) {
        String savePath;
        try {
            savePath = getSavePath(image, imageFile, SAVE_PATH_PREFIX);
        } catch (Exception e) {
            LOGGER.error("fail to deal with logoUrl, logo:{}, logoFile:{}, error:{}",
                    image, imageFile, e);
            return redirect(addErrorInfoParameter(ADMIN_DETAIL, "图片文件处理失败!"));
        }

        link = new LinkAdapter().adjust(link);
        AppConfigDetailImageDao dao = new AppConfigDetailImageDao();
        if (id != null && id >= 0) {
            dao.edit(id, savePath, link);
            logAction("将商品详细页配置第'%d'个配置图片修改为'%s'", id, savePath);
        } else {
            dao.add(savePath, link);
            logAction("为商品详细页配置添加图片'%s'", savePath);
        }

        return redirect(ADMIN_DETAIL);
    }

    @ResponseBody
    @RequestMapping(value = "/admin-detail-image-delete", method = RequestMethod.POST)
    public Map<String, Object> deleteImage(@RequestParam(value = "id", required = true) Integer id) {
        AppConfigDetailImageDao dao = new AppConfigDetailImageDao();
        if (id != null && id >= 0) {
            dao.delete(id);
            logAction("删除商品详细页配置第'%d'个配置图片", id);
        }
        return SUCCESS;
    }

}
