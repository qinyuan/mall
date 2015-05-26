package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.CommodityPictureDao;
import com.qinyuan15.mall.core.image.PictureUrlConverter;
import com.qinyuan15.mall.core.image.PictureUrlValidator;
import com.qinyuan15.utils.image.ImageDownloader;
import com.qinyuan15.utils.image.MultiImageDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to download commodity picture and save them to database
 * Created by qinyuan on 15-2-24.
 */
public class CommodityPictureDownloader {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPictureDownloader.class);
    private ImageDownloader imageDownloader;
    private PictureUrlConverter pictureUrlConverter;
    private PictureUrlValidator urlValidator;
    private int threadSize;

    public CommodityPictureDownloader(ImageDownloader imageDownloader, PictureUrlConverter pictureUrlConverter) {
        this.imageDownloader = imageDownloader;
        this.pictureUrlConverter = pictureUrlConverter;
    }

    public CommodityPictureDownloader setThreadSize(int threadSize) {
        this.threadSize = threadSize;
        return this;
    }

    public void setLocalAddress(String localAddress) {
        this.urlValidator = new PictureUrlValidator(localAddress);
    }

    public void save(Integer commodityId, List<String> imageUrls) {
        List<String> savePaths = download(imageUrls);
        new CommodityPictureDao().save(commodityId, savePaths);
        LOGGER.info("save {} picture of commodity {}", savePaths.size(), commodityId);
    }

    public void saveDetail(Integer commodityId, List<String> imageUrls) {
        List<String> savePaths = download(imageUrls);
        new CommodityPictureDao().saveDetail(commodityId, savePaths);
        LOGGER.info("save {} picture of commodity {}", savePaths.size(), commodityId);
    }

    private List<String> download(List<String> imageUrls) {
        MultiImageDownloader multiImageDownloader = new MultiImageDownloader(imageDownloader);
        if (threadSize > 0) {
            multiImageDownloader.setThreadSize(threadSize);
        }

        List<String> paths = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            if (this.urlValidator != null && this.pictureUrlConverter != null
                    && this.urlValidator.isLocal(imageUrl)) {
                paths.add(pictureUrlConverter.urlToPath(imageUrl));
            } else {
                multiImageDownloader.addUrl(imageUrl);
            }
        }
        paths.addAll(multiImageDownloader.download());
        return paths;
    }

    public void clearAndSave(Integer commodityId, List<String> imageUrls) {
        CommodityPictureDao dao = new CommodityPictureDao();
        dao.deleteInstances(commodityId);
        save(commodityId, imageUrls);
    }

    public void clearAndSaveDetail(Integer commodityId, List<String> imageUrls) {
        CommodityPictureDao dao = new CommodityPictureDao();
        dao.deleteDetailInstances(commodityId);
        saveDetail(commodityId, imageUrls);
    }
}
