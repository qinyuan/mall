package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao of CommodityPicture
 * Created by qinyuan on 15-1-15.
 */
public class CommodityPictureDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPictureDao.class);

    /**
     * Save normal commodity picture to database
     *
     * @param commodityId id of commodity
     * @param urls        url of commodities
     */
    public void save(Integer commodityId, List<String> urls) {
        save(commodityId, urls, false);
    }

    /**
     * Save detail commodity picture to database
     *
     * @param commodityId id of commodity
     * @param urls        url of commodities
     */
    public void saveDetail(Integer commodityId, List<String> urls) {
        save(commodityId, urls, true);
    }

    /**
     * Save normal/detail commodity picture to database
     *
     * @param commodityId id of commodity
     * @param urls        url of commodities
     * @param detail      detail picture or normal picture
     */
    public void save(Integer commodityId, List<String> urls, boolean detail) {
        for (String url : urls) {
            if (HibernateUtils.getCount(CommodityPicture.class,
                    "commodityId=" + commodityId + " AND url='" + url + "'") > 0) {
                LOGGER.warn("picture {} of commodityId {} already exists, give up saving it to database",
                        url, commodityId);
                continue;   // If picture already exists, skip it
            }
            CommodityPicture picture = new CommodityPicture();
            picture.setCommodityId(commodityId);
            picture.setUrl(url);
            picture.setDetail(detail);
            try {
                HibernateUtils.save(picture);
                LOGGER.info("save picture {} of commodityId {} to database",
                        url, commodityId);
            } catch (Exception e) {
                LOGGER.error("fail to save picture {} of commodityId {} to database: {}",
                        url, commodityId, e);
            }
        }
    }

    public List<CommodityPicture> getDetailInstances(Integer commodityId) {
        return getInstances(commodityId, true);
    }

    @SuppressWarnings("unchecked")
    private List<CommodityPicture> getInstances(Integer commodityId, boolean detail) {
        String hql = "FROM CommodityPicture WHERE commodityId=" + commodityId + " AND detail=" + detail;
        return HibernateUtils.getList(hql);
    }

    public List<CommodityPicture> getInstances(Integer commodityId) {
        return getInstances(commodityId, false);
    }

    public CommodityPicture getFirstInstance(Integer commodityId) {
        return HibernateUtils.getFirstItem(CommodityPicture.class,
                "commodityId=" + commodityId + " ORDER BY id ASC");
    }

    public List<CommodityPicture> getFirstInstances(List<Integer> commodityIds) {
        List<CommodityPicture> commodityPictures = new ArrayList<>();
        for (Integer commodityId : commodityIds) {
            commodityPictures.add(getFirstInstance(commodityId));
        }
        return commodityPictures;
    }

    public void deleteInstances(Integer commodityId) {
        deleteInstances(commodityId, false);
    }

    public void deleteInstances(Integer commodityId, boolean detail) {
        HibernateUtils.delete(CommodityPicture.class,
                "commodityId=" + commodityId + " AND detail=" + detail);
    }

    public void deleteDetailInstances(Integer commodityId) {
        deleteInstances(commodityId, true);
    }

    /**
     * Judge whether one commodity has picture
     *
     * @param commodityId id of commodity
     * @return if has picture, return true, else return false
     */
    public boolean hasPicture(Integer commodityId) {
        return HibernateUtils.getCount(CommodityPicture.class, "commodityId=" + commodityId) > 0;
    }
}
