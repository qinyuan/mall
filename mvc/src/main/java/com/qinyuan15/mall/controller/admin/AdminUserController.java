package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.UserDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.security.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Page to edit user
 * Created by qinyuan on 15-3-28.
 */
@Controller
public class AdminUserController extends ImageController {
    @RequestMapping("/admin-user")
    public String index(ModelMap model) {
        model.addAttribute("users", new UserDao().getInstances());
        addCssAndJs("admin-normal-edit-page");
        setTitle("用户管理");
        return "admin-user";
    }

    @ResponseBody
    @RequestMapping(value = "/admin-user-add-update", method = RequestMethod.POST)
    public Map<String, Object> addUpdate(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "username", required = true) String username,
                                         @RequestParam(value = "password", required = true) String password) {
        if (!StringUtils.hasText(username)) {
            return createFailResult("用户名不能为空");
        }
        if (!StringUtils.hasText(password)) {
            return createFailResult("密码不能为空");
        }

        UserDao userDao = new UserDao();
        if (isPositive(id)) {
            User user = userDao.getInstance(id);
            logAction("更新用用户'%s'", user.getUsername());
            user.setUsername(username);
            user.setPassword(password);
            HibernateUtils.update(user);
        } else {
            if (userDao.getInstanceByName(username) != null) {
                return createFailResult("用户名'" + username + "'已经存在");
            }
            userDao.addAdmin(username, password);
            logAction("添加用户'%s'", username);
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "/admin-user-delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Integer id) {
        if (isPositive(id)) {
            if (id.equals(securitySearcher.getUserId())) {
                createFailResult("不能将用户自己删除");
            }

            UserDao dao = new UserDao();
            User user = dao.getInstance(id);
            logAction("删除用户'%s'", user.getUsername());
            dao.delete(id);
        }
        return SUCCESS;
    }
}
