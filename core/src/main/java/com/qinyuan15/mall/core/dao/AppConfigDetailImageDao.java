package com.qinyuan15.mall.core.dao;


import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Dao of AppConfig
 * Created by qinyuan on 15-3-22.
 */
public class AppConfigDetailImageDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppConfigDetailImage.class);

    public List<AppConfigDetailImage> getInstances() {
        return HibernateUtils.getList(AppConfigDetailImage.class);
    }

    public AppConfigDetailImage getInstance(Integer id) {
        return HibernateUtils.get(AppConfigDetailImage.class, id);
    }

    public void add(String path, String link) {
        AppConfigDetailImage image = new AppConfigDetailImage();
        image.setId(HibernateUtils.getMaxId(AppConfigDetailImage.class) + 1);
        image.setPath(path);
        image.setLink(link);
        HibernateUtils.save(image);
    }

    public void edit(Integer id, String path, String link) {
        AppConfigDetailImage image = getInstance(id);
        if (image == null) {
            LOGGER.error("fail to update AppConfigDetailImage instance whose id is {}", id);
            return;
        }

        image.setPath(path);
        image.setLink(link);
        HibernateUtils.update(image);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(AppConfigDetailImage.class, id);
    }
}
