package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Dao of AppConfigFootLink
 * Created by qinyuan on 15-4-13.
 */
public class AppConfigFootLinkDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppConfigFootLink.class);

    public String getTextById(Integer id) {
        AppConfigFootLink footLink = getInstance(id);
        return footLink == null ? null : footLink.getText();
    }

    public AppConfigFootLink getInstance(Integer id) {
        return HibernateUtils.get(AppConfigFootLink.class, id);
    }

    public List<AppConfigFootLink> getInstances() {
        return new RankingDao().getInstances(AppConfigFootLink.class);
    }

    public void clear() {
        HibernateUtils.deleteAll(AppConfigFootLink.class);
    }

    public Integer add(String text, String link) {
        AppConfigFootLink footLink = new AppConfigFootLink();
        footLink.setText(text);
        footLink.setLink(link);
        return new RankingDao().add(footLink);
    }

    public void edit(Integer id, String text, String link) {
        AppConfigFootLink image = getInstance(id);
        if (image == null) {
            LOGGER.error("fail to update AppConfigFootLink instance whose id is {}", id);
        } else {
            image.setText(text);
            image.setLink(link);
            HibernateUtils.update(image);
        }
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(AppConfigFootLink.class, id);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(AppConfigFootLink.class, id);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(AppConfigFootLink.class, id);
    }
}
