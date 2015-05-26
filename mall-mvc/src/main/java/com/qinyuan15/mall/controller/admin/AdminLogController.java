package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.UserLogDao;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminLogController extends ImageController {
    @RequestMapping("/admin-log")
    public String index() {
        UserLogDao.Factory factory = UserLogDao.factory()
                .setUserId(securitySearcher.getUserId());
        new PaginationAttributeAdder(factory, request).setRowItemsName("userLogs").add();

        addCssAndJs("admin-normal-edit-page");
        setTitle("操作日志");
        return "admin-log";
    }
}
