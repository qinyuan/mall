package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.config.LinkAdapter;
import com.qinyuan15.mall.core.dao.CategoryDao;
import com.qinyuan15.mall.core.dao.CategoryPoster;
import com.qinyuan15.mall.core.dao.CategoryPosterDao;
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
 * Controller to edit category poster relation
 * Created by qinyuan on 15-3-25.
 */
@Controller
public class AdminCategoryPosterController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminCategoryPosterController.class);
    private final static String SAVE_PATH_PREFIX = "mall/category/poster/";
    private final static String EDIT_PAGE = "admin-category";

    @RequestMapping(value = "/admin-category-poster-add-update", method = RequestMethod.POST)
    public String addUpdate(@RequestParam(value = "id", required = true) Integer id,
                            @RequestParam(value = "categoryId", required = true) Integer categoryId,
                            @RequestParam(value = "poster", required = true) String poster,
                            @RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
                            @RequestParam(value = "link", required = true) String link) {
        String path;
        try {
            path = getSavePath(poster, posterFile, SAVE_PATH_PREFIX);
        } catch (Exception e) {
            LOGGER.error("fail to deal with logoUrl, logo:{}, logoFile:{}, error:{}"
                    , poster, posterFile, e);
            return redirect(addErrorInfoParameter(EDIT_PAGE, "海报图片文件处理失败!"));
        }

        link = new LinkAdapter().adjust(link);

        CategoryPosterDao dao = new CategoryPosterDao();
        if (isPositive(id)) {
            dao.update(id, categoryId, path, link);
            logAction("为'%s'分类更新海报'%s'", new CategoryDao().getNameById(categoryId),
                    pictureUrlConverter.pathToUrl(path));
        } else {
            dao.add(categoryId, path, link);
            logAction("为'%s'分类添加海报'%s'", new CategoryDao().getNameById(categoryId),
                    pictureUrlConverter.pathToUrl(path));
        }

        return redirect(EDIT_PAGE);
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-poster-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            CategoryPosterDao dao = new CategoryPosterDao();
            CategoryPoster categoryPoster = dao.getInstance(id);
            logAction("删除'%s'分类中的'%s'海报", categoryPoster.getCategoryName(),
                    pictureUrlConverter.pathToUrl(categoryPoster.getPath()));
            dao.delete(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-poster-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            CategoryPosterDao dao = new CategoryPosterDao();
            CategoryPoster categoryPoster = dao.getInstance(id);
            logAction("上移'%s'分类中'%s'海报的排序", categoryPoster.getCategoryName(),
                    pictureUrlConverter.pathToUrl(categoryPoster.getPath()));
            dao.rankUp(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-category-poster-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            CategoryPosterDao dao = new CategoryPosterDao();
            CategoryPoster categoryPoster = dao.getInstance(id);
            logAction("下移'%s'分类中'%s'海报的排序", categoryPoster.getCategoryName(),
                    pictureUrlConverter.pathToUrl(categoryPoster.getPath()));
            dao.rankDown(id);
        }
        return SUCCESS;
    }
}
