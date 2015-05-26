package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.security.User;
import org.junit.Test;

/**
 * Test UserDao
 * Created by qinyuan on 15-3-5.
 */
public class UserDaoTest {
    private UserDao dao = new UserDao();

    @Test
    public void testGetInstanceByName() throws Exception {
        User user = dao.getInstanceByName("admin");
        if (user != null) {
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getRole());
        }
    }

    @Test
    public void testGetIdByName() throws Exception {
        System.out.println(dao.getIdByName("admin"));
        System.out.println(dao.getIdByName("manager"));
    }
}
