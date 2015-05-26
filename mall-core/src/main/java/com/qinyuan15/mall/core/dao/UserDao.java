package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.security.IUserDao;
import com.qinyuan15.utils.security.SecuritySearcher;
import com.qinyuan15.utils.security.User;

import java.util.List;

/**
 * Dao object of User
 * Created by qinyuan on 15-3-5.
 */
public class UserDao implements IUserDao {
    public User getInstance(Integer userId) {
        return HibernateUtils.get(User.class, userId);
    }

    public User getInstanceByName(String username) {
        return new HibernateListBuilder()
                .addFilter("username=:username").addArgument("username", username)
                .getFirstItem(User.class);
    }

    public List<User> getInstances() {
        return HibernateUtils.getList(User.class);
    }

    public Integer getIdByName(String username) {
        User user = this.getInstanceByName(username);
        return user == null ? null : user.getId();
    }

    public String getNameById(Integer userId) {
        User user = getInstance(userId);
        return user == null ? null : user.getUsername();
    }

    public Integer addAdmin(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(SecuritySearcher.ADMIN);
        return HibernateUtils.save(user);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(User.class, id);
    }
}
