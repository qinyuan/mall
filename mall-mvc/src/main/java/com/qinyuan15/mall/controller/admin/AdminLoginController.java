package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller about login
 * Created by qinyuan on 15-3-5.
 */
@Controller
public class AdminLoginController extends BaseController {
    @RequestMapping("/login")
    public String index() {
        addCss("index");
        setTitle("用户登录");
        addIEJs("login-ie-patch");
        return "login";
    }
}
