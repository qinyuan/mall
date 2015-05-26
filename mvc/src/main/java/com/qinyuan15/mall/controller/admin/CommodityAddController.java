package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.commodity.CommodityPictureDownloader;
import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class to define some useful methods about editing Commodity
 * Created by qinyuan on 15-5-2.
 */
@Component
public class CommodityAddController extends ImageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityAddController.class);

    protected Integer getCategoryId(Integer id1, Integer id2) {
        if (!isPositive(id2)) {
            return id1;
        } else {
            return id2;
        }
    }

    protected Integer getBranchId(Integer id1, Integer id2, Integer id3) {
        if (!isPositive(id2)) {
            return id1;
        } else if (!isPositive(id3)) {
            return id2;
        } else {
            return id3;
        }
    }

    protected String newCommoditySerialNumber() {
        return RandomStringUtils.randomNumeric(12);
    }

    protected Commodity newCommodity() {
        Commodity commodity = new Commodity();
        commodity.setSerialNumber(newCommoditySerialNumber());
        return commodity;
    }

    protected Integer addCommodity(Commodity commodity) {
        try {
            commodity.setActive(true);
            commodity.setUserId(securitySearcher.getUserId());
            commodity.setInLowPrice(false);
            if (commodity.getCrawlDate() == null) {
                commodity.setCrawlDate("1900-01-01");
            }
            if (commodity.getSerialNumber() == null) {
                commodity.setSerialNumber(newCommoditySerialNumber());
            }

            Integer id = HibernateUtils.save(commodity);

            logAction("添加商品'%s'", commodity.getName());
            LOGGER.info("Insert new Commodity {}", id);
            return id;
        } catch (Exception e) {
            logAction("添加商品'%s'失败", commodity.getName());
            LOGGER.error("fail to insert new Commodity: {}", e);
            throw e;
        }
    }

    protected void addImages(Integer id, String[] imageUrls) {
        if (imageUrls == null) {
            imageUrls = new String[0];
        }
        addImages(id, Arrays.asList(imageUrls));
    }

    protected void addImages(Integer id, List<String> imageUrls) {
        CommodityPictureDownloader pictureDownloader = getCommodityPictureDownloader();
        try {
            if (imageUrls == null) {
                imageUrls = new ArrayList<>();
            }
            LOGGER.info("start saving images");
            pictureDownloader.clearAndSave(id, imageUrls);
            LOGGER.info("complete saving images");
        } catch (Exception e) {
            LOGGER.error("fail to save commodity images, imageUrls: {}, info: {}",
                    imageUrls, e);
            throw e;
        }
    }

    protected void addDetailImages(Integer id, List<String> detailImageUrls) {
        CommodityPictureDownloader pictureDownloader = getCommodityPictureDownloader();
        try {
            if (detailImageUrls == null) {
                detailImageUrls = new ArrayList<>();
            }
            LOGGER.info("start saving detail images");
            pictureDownloader.clearAndSaveDetail(id, detailImageUrls);
            LOGGER.info("complete saving detail images");
        } catch (Exception e) {
            LOGGER.error("fail to save commodity detail images, detailImageUrls: {}, info: {}",
                    detailImageUrls, e);
            throw e;
        }
    }

    protected void addDetailImages(Integer id, String[] detailImageUrls) {
        if (detailImageUrls == null) {
            detailImageUrls = new String[0];
        }
        addDetailImages(id, Arrays.asList(detailImageUrls));
    }

    private final static int DEFAULT_DOWNLOAD_THREAD_SIZE = 10;

    private CommodityPictureDownloader getCommodityPictureDownloader() {
        CommodityPictureDownloader pictureDownloader = new CommodityPictureDownloader(
                imageDownloader, pictureUrlConverter).setThreadSize(DEFAULT_DOWNLOAD_THREAD_SIZE);
        pictureDownloader.setLocalAddress(getLocalAddress());
        return pictureDownloader;
    }
}
