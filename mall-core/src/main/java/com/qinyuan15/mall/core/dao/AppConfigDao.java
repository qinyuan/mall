package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;

/**
 * Dao of AppConfig
 * Created by qinyuan on 15-3-22.
 */
public class AppConfigDao {
    public AppConfig getInstance() {
        return HibernateUtils.getFirstItem(AppConfig.class);
    }

    public void update(AppConfig appConfig) {
        HibernateUtils.update(appConfig);
    }
}
