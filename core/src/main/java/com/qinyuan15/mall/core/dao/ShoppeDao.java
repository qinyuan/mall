package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * Data access object of Shoppe
 * Created by qinyuan on 15-3-1.
 */
public class ShoppeDao {

    public List<Shoppe> getInstances(int branchId) {
        return HibernateUtils.getList(Shoppe.class, "WHERE branchId=" + branchId);
    }

    public void clear(int branchId) {
        HibernateUtils.delete(Shoppe.class, "branchId=" + branchId);
    }

    public void save(List<Shoppe> shoppes) {
        Session session = HibernateUtils.getSession();
        for (Shoppe shoppe : shoppes) {
            session.save(shoppe);
        }
        HibernateUtils.commit(session);
    }
}
