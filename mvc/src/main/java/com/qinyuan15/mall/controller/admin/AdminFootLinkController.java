package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.config.DefaultAppConfigFootLinks;
import com.qinyuan15.mall.core.config.LinkAdapter;
import com.qinyuan15.mall.core.dao.AppConfigFootLink;
import com.qinyuan15.mall.core.dao.AppConfigFootLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Page to edit foot link
 * Created by qinyuan on 15-3-19.
 */
@Controller
public class AdminFootLinkController extends ImageController {
    private AppConfigFootLinkDao dao = new AppConfigFootLinkDao();

    @Autowired
    private DefaultAppConfigFootLinks defaultAppConfigFootLinks;

    @ResponseBody
    @RequestMapping(value = "/admin-foot-link-add-update", method = RequestMethod.POST)
    public Map<String, Object> addUpdate(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "text", required = true) String text,
                                         @RequestParam(value = "link", required = true) String link) {
        if (!StringUtils.hasText(text)) {
            return createFailResult("显示文字不能为空");
        }
        if (!StringUtils.hasText(link)) {
            return createFailResult("目标链接不能为空");
        }

        // adjust link
        link = new LinkAdapter().adjust(link);

        if (isPositive(id)) {
            dao.edit(id, text, link);
            logAction("更新页尾链接'%s'", text);
        } else {
            dao.add(text, link);
            logAction("添加页尾链接'%s'", text);
        }

        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-foot-link-reset", method = RequestMethod.POST)
    public Map<String, Object> reset() {
        dao.clear();
        for (AppConfigFootLink footLink : defaultAppConfigFootLinks.getFootLinks()) {
            dao.add(footLink.getText(), footLink.getLink());
            logAction("将页尾链接恢复为默认值");
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-foot-link-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("删除页尾链接'%s'", dao.getTextById(id));
            dao.delete(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-foot-link-rank-up", method = RequestMethod.POST)
    public Map<String, Object> rankUp(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("上移页尾链接'%s'的排序", dao.getTextById(id));
            dao.rankUp(id);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-foot-link-rank-down", method = RequestMethod.POST)
    public Map<String, Object> rankDown(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            logAction("下移页尾链接'%s'的排序", dao.getTextById(id));
            dao.rankDown(id);
        }
        return SUCCESS;
    }
}
