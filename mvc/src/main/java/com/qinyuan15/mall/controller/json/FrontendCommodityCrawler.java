package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.core.commodity.CommodityBuyUrlBuilder;
import com.qinyuan15.mall.core.dao.AppConfig;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.mall.crawler.EtaoCrawler;
import com.qinyuan15.mall.crawler.html.CommodityPageParser;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.html.CommodityPageValidator;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.concurrent.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * class to crawl commodity information
 * Created by qinyuan on 15-5-12.
 */
public class FrontendCommodityCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(FrontendCommodityCrawler.class);
    private Integer pictureSize;
    private Integer detailPictureSize;
    private CommodityPageParserBuilder pageParserBuilder;

    public FrontendCommodityCrawler(AppConfig appConfig, CommodityPageParserBuilder pageParserBuilder) {
        if (appConfig != null) {
            pictureSize = appConfig.getMaxCommodityPictureSize();
            detailPictureSize = appConfig.getMaxCommodityDetailPictureSize();
        }
        this.pageParserBuilder = pageParserBuilder;
    }

    private final static int MAX_THREAD_POOL_SIZE = 6;

    public Queue<CommodityJson> get(String[] showIds, String[] urls) {
        Queue<CommodityJson> queue = new ConcurrentLinkedQueue<>();
        int size = Math.min(showIds.length, urls.length);
        ThreadPool threadPool = new ThreadPool(Math.min(size, MAX_THREAD_POOL_SIZE));
        for (int i = 0; i < size; i++) {
            threadPool.add(new SingleCommodityCrawlThread(showIds[i], urls[i], queue));
        }
        threadPool.waitToComplete();
        return queue;
    }

    private class SingleCommodityCrawlThread extends Thread {
        private String showId;
        private String url;
        private Queue<CommodityJson> queue;

        private SingleCommodityCrawlThread(String showId, String url, Queue<CommodityJson> queue) {
            this.showId = showId;
            this.url = url;
            this.queue = queue;
        }

        @Override
        public void run() {
            CommodityJson json = get(null, showId, url);
            queue.add(json);
        }
    }

    public CommodityJson get(Integer id, String showId, String url) {
        // check showId
        if (new CommodityDao().hasDuplicateShowId(id, showId)) {
            return buildFailJson("商品ID'" + showId + "'已经存在!");
        }

        // check page content
        String content = getPageContent(url);

        if (!StringUtils.hasText(content)) {
            return getInvalidUrlResult(url);
        }

        CommodityPageParser parser = pageParserBuilder.build(url, content);
        if (parser.isExpire()) {
            return getInvalidUrlResult(url);
        }

        if (!new CommodityPageValidator().validate(parser, url)) {
            LOGGER.info("response of requesting to {}:\n{}",
                    url, content);
            return buildFailJson("网页'" + url + "'解析出错!");
        }

        // parse commodity information from page content
        CommodityJson json = new CommodityJson();
        json.success = true;
        json.showId = showId;
        json.crawlerUrl = url;
        json.name = parser.getName();
        json.buyUrl = new CommodityBuyUrlBuilder().getBuyUrl(url);
        json.imageUrls = parser.getImageUrls();
        json.detailImageUrls = parser.getDetailImagesUrls();

        // adjust image picture url and image detail picture url
        if (IntegerUtils.isPositive(this.pictureSize) &&
                this.pictureSize < json.imageUrls.size()) {
            json.imageUrls = json.imageUrls.subList(0, this.pictureSize);
        }
        if (IntegerUtils.isPositive(detailPictureSize) &&
                detailPictureSize < json.detailImageUrls.size()) {
            json.detailImageUrls = json.detailImageUrls.subList(0, detailPictureSize);
        }

        return json;
    }

    private String getPageContent(String url) {
        EtaoCrawler etaoCrawler = new EtaoCrawler(url, pageParserBuilder);
        return etaoCrawler.getContent();
    }

    private CommodityJson buildFailJson(String info) {
        CommodityJson json = new CommodityJson();
        json.success = false;
        json.detail = info;
        return json;
    }

    private CommodityJson getInvalidUrlResult(String url) {
        return buildFailJson("商品链接'" + url + "'无效!");
    }

    public static class CommodityJson {
        boolean success;
        String showId;
        String crawlerUrl;
        String detail;
        String name;
        String buyUrl;
        List<String> imageUrls;
        List<String> detailImageUrls;
    }
}
