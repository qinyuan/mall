package com.qinyuan15.mall.core.config;

import com.qinyuan15.mall.core.dao.AppConfig;
import com.qinyuan15.mall.core.dao.AppConfigDetailImage;
import com.qinyuan15.mall.core.image.PictureUrlConverter;

/**
 * Adjust url of AppConfig
 * Created by qinyuan on 15-2-25.
 */
public class AppConfigAdapter {
    private PictureUrlConverter pictureUrlConverter;

    public AppConfigAdapter(PictureUrlConverter pictureUrlConverter) {
        this.pictureUrlConverter = pictureUrlConverter;
    }

    public AppConfig adjust(AppConfig appConfig) {
        if (appConfig != null && pictureUrlConverter != null) {
            appConfig.setGlobalLogo(pictureUrlConverter.pathToUrl(appConfig.getGlobalLogo()));
            appConfig.setGlobalBanner(pictureUrlConverter.pathToUrl(appConfig.getGlobalBanner()));
            appConfig.setIndexHeadPoster(pictureUrlConverter.pathToUrl(appConfig.getIndexHeadPoster()));
            appConfig.setIndexFootPoster(pictureUrlConverter.pathToUrl(appConfig.getIndexFootPoster()));
            appConfig.setBranchRankImage(pictureUrlConverter.pathToUrl(appConfig.getBranchRankImage()));
            appConfig.setNoFoundImage(pictureUrlConverter.pathToUrl(appConfig.getNoFoundImage()));
            appConfig.setFavicon(pictureUrlConverter.pathToUrl(appConfig.getFavicon()));

            for (AppConfigDetailImage image : appConfig.getDetailImages()) {
                image.setPath(pictureUrlConverter.pathToUrl(image.getPath()));
            }
        }

        return appConfig;
    }
}
