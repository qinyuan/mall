package com.qinyuan15.mall.crawler.html;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface to parse commodity web page
 * Created by qinyuan on 15-1-2.
 */
public interface CommodityPageParser {
    String getName();

    /**
     * @return A map that contains price history information,
     * key of map is date and value of map is price of that date
     */
    Map<Date, Double> getPriceHistory();

    String getUrl();

    List<String> getImageUrls();

    List<String> getDetailImagesUrls();

    Integer getSales();

    boolean isExpire();

    boolean isRejected();
}
