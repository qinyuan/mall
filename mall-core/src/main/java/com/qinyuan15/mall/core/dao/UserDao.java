package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.security.IUserDao;
import com.qinyuan15.utils.security.SecuritySearcher;
import com.qinyuan15.utils.security.User;
import org.hibernate.Session;

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
        Session session = HibernateUtils.getSession();
        List list = session.createQuery("FROM User WHERE username=:username")
                .setString("username", username).list();
        User user = list.size() == 0 ? null : (User) list.get(0);
        HibernateUtils.commit(session);
        return user;
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
