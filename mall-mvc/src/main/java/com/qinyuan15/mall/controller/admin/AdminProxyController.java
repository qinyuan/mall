package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.utils.http.ProxyDao;
import com.qinyuan15.utils.http.ProxyRejectionDao;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminProxyController extends ImageController {
    @RequestMapping("/admin-proxy")
    public String index(ModelMap model) {
        ProxyRejectionDao.Factory factory = ProxyRejectionDao.factory();
        new PaginationAttributeAdder(factory, request).setRowItemsName("rejections").add();

        ProxyDao proxyDao = new ProxyDao();
        model.addAttribute("proxyCount", proxyDao.getCount());
        model.addAttribute("fastProxyCount", proxyDao.getFastCount());

        addCssAndJs("admin-normal-edit-page");
        setTitle("代理拦截记录");
        return "admin-proxy";
    }
}
