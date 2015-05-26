package com.qinyuan15.mall.core.commodity;

/**
 * Class to build commodity buy url
 * Created by qinyuan on 15-5-16.
 */
public class CommodityBuyUrlBuilder {
    // TODO method to calculate buy url should be improved
    public String getBuyUrl(String url) {
        return url.replace(".html", "").replace("http://s.etao.com/detail/",
                "http://detail.tmall.com/item.htm?id=");
    }
}
