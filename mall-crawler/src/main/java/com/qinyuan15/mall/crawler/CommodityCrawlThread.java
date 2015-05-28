package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.core.commodity.CommodityPool;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.mall.core.dao.SimpleCommodity;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.http.HttpClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread to crawl commodity information
 * Created by qinyuan on 15-5-28.
 */
public class CommodityCrawlThread extends StoptableThread {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityCrawlThread.class);
    private final SingleCommodityCrawler singleCommodityCrawler;
    private final CommodityPool commodityPool;
    private final CrawlTimeValidator crawlTimeValidator;
    private boolean debugMode;

    public CommodityCrawlThread(CommodityPool commodityPool,
                                HttpClientPool httpClientPool,
                                CommodityPageParserBuilder commodityPageParserBuilder,
                                ExpireCommodityRecorder expireCommodityRecorder,
                                CrawlTimeValidator crawlTimeValidator) {
        this.singleCommodityCrawler = new SingleCommodityCrawler(
                httpClientPool, commodityPageParserBuilder, expireCommodityRecorder);
        this.commodityPool = commodityPool;
        this.crawlTimeValidator = crawlTimeValidator;
    }

    public CommodityCrawlThread setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    @Override
    protected void jobToRun() {
        if (commodityPool == null) {
            LOGGER.info("commodity pool is null, no commodity history to grub.");
            return;
        }

        //ThreadUtils.sleep(interval);
        SimpleCommodity commodity = null;
        try {
            commodity = commodityPool.next();
            if (commodity == null) {
                LOGGER.info("no commodity to crawl.");
                return;
            }

            if (debugMode) {
                LOGGER.info("deal with commodity {}", commodity.getName());
                return;
            }

            if (crawlTimeValidator.inCrawlTime()) {
                if (DateUtils.now().toString().equals(commodity.getCrawlDate())) {
                    LOGGER.info("Today's price of commodity {} already save, just skip it",
                            commodity.getName());
                } else {
                    this.singleCommodityCrawler.save(commodity);
                }
            }
            new CommodityDao().updateInLowPrice(commodity.getId());
        } catch (Throwable e) {
            String commodityInfo = commodity == null ?
                    null : commodity.getName() + "(" + commodity.getUrl() + ")";
            LOGGER.error("fail to grub commodity '{}': {}",
                    commodityInfo, e);
        }
    }
}
