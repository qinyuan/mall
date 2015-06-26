package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.utils.http.ProxyActivator;
import com.qinyuan15.utils.http.ProxyDao;
import com.qinyuan15.utils.http.ProxyRejectionDao;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminProxyController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminProxyController.class);

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

    @RequestMapping("/admin-proxy-reactivate.json")
    @ResponseBody
    public Map<String, Object> reactivate(@RequestParam(value = "proxyRejectionId", required = true) Integer proxyRejectionId) {
        if (isPositive(proxyRejectionId)) {
            try {
                new ProxyActivator().activateByProxyRejectionId(proxyRejectionId);
                return SUCCESS;
            } catch (Exception e) {
                LOGGER.error("fail to reactivate proxy, id: {}, info: {}", proxyRejectionId, e);
                return createFailResult("数据库操作失败");
            }
        } else {
            return createFailResult("数据格式错误！");
        }
    }
}
