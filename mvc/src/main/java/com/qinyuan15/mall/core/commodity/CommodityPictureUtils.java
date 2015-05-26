package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.CommodityPicture;

import java.util.ArrayList;
import java.util.List;

/**
 * Tool Class about CommodityPicture
 * Created by qinyuan on 15-3-20.
 */
public class CommodityPictureUtils {
    private CommodityPictureUtils() {
    }

    public static List<String> getUrls(List<CommodityPicture> commodityPictures) {
        List<String> urls = new ArrayList<>();
        for (CommodityPicture commodityPicture : commodityPictures) {
            if (commodityPicture != null) {
                urls.add(commodityPicture.getUrl());
            }
        }
        return urls;
    }
}
