package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.mall.core.dao.CommodityPriceDao;
import com.qinyuan15.mall.core.dao.SimpleCommodity;
import com.qinyuan15.mall.crawler.html.CommodityPageParser;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.html.CommodityPageValidator;
import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.HttpClientPool;
import com.qinyuan15.utils.http.HttpExceptionUtils;
import com.qinyuan15.utils.http.IProxy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Date;
import java.util.Map;

/**
 * Grub price of single commodity, then save the price history of it to database
 * Created by qinyuan on 15-1-1.
 */
class SingleCommodityCrawler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SingleCommodityCrawler.class);

    private final CommodityDao commodityDao = new CommodityDao();
    private final CommodityCrawlLogDao crawlLogDao = new CommodityCrawlLogDao();
    private final HttpClientPool httpClientPool;
    private final CommodityPageParserBuilder pageParserBuilder;
    private final ExpireCommodityRecorder expireCommodityRecorder;

    public SingleCommodityCrawler(HttpClientPool httpClientPool,
                                  CommodityPageParserBuilder pageParserBuilder,
                                  ExpireCommodityRecorder expireCommodityRecorder) {
        this.httpClientPool = httpClientPool;
        this.pageParserBuilder = pageParserBuilder;
        this.expireCommodityRecorder = expireCommodityRecorder;
    }

    /**
     * Save price history of certain Commodity to database
     *
     * @param commodity Commodity object to save
     */
    public void save(SimpleCommodity commodity) {
        String url = commodity.getUrl();
        HttpClient client = this.httpClientPool.next();
        Integer commodityId = commodity.getId();
        try {
            LOGGER.info("prepare to save price history of {}", url);

            String html = client.getContent(url);
            CommodityPageParser parser = pageParserBuilder.build(url, html);

            if (!validateExpiration(parser, commodityId)) {
                return;
            }

            IProxy proxy = client.getProxy();
            if (!new CommodityPageValidator().validate(parser, url)) {
                LOGGER.info("invalid response of requesting to {} with proxy {}:\n{}",
                        url, proxy, html);

                crawlLogDao.logFail(commodityId, "网页内容有误");
                if (CommodityPageValidator.getFailTimes(proxy) >= 5) {
                    client.feedbackRejection();
                    LOGGER.warn("proxy {} reach to fail times", proxy);

                    // log invalid html
                    if (!new File("/tmp/invalid").isDirectory()) {
                        new File("/tmp/invalid").mkdirs();
                    }
                    File logFile = new File("/tmp/invalid/" + RandomUtils.nextInt(0, 100) + System.currentTimeMillis()
                            + ".html");
                    FileUtils.write(logFile, html);
                }
                return;
            } else {
                CommodityPageValidator.resetFailTimes(proxy);
            }

            updateSales(parser, commodity);

            if (updatePriceHistory(parser, commodityId)) {
                LOGGER.info("save price history of {}", url);
            } else {
                LOGGER.info("can not get priceHistory from url {}, html contents: {}",
                        url, html);
                client.feedbackRejection();
            }
        } catch (Throwable e) {
            LOGGER.error("fail to fetch price history of {}: {}", url, e);
            if (HttpExceptionUtils.isHttpException(e)) {
                crawlLogDao.logFail(commodityId, "代理服务器或网页连接失败");
            } else {
                crawlLogDao.logFail(commodityId, "未知错误");
            }
        }
    }

    private boolean updatePriceHistory(CommodityPageParser commodityPageParser, int commodityId) {
        Map<Date, Double> priceHistory = commodityPageParser.getPriceHistory();
        if (priceHistory == null) {
            crawlLogDao.logFail(commodityId, "价格记录抓取失败：网页解析错误");
            return false;
        } else {
            new CommodityPriceDao().updatePriceHistory(priceHistory, commodityId);
            crawlLogDao.logSuccess(commodityId, "价格记录抓取成功");
            return true;
        }
    }

    private void updateSales(CommodityPageParser commodityPageParser, SimpleCommodity commodity) {
        Integer sales = commodityPageParser.getSales();
        if (sales == null) {
            LOGGER.error("fail to parse sales of commodity {}", commodity.getName());
        } else {
            commodityDao.updateSales(commodity.getId(), sales);
            LOGGER.info("update sales of commodity {} to {}", commodity.getName(),
                    sales);
        }
    }

    private boolean validateExpiration(CommodityPageParser commodityPageParser, int commodityId) {
        if (commodityPageParser.isExpire()) {
            String logInfo = "网已过期，第" + (expireCommodityRecorder.getFailTimes(commodityId) + 1)
                    + "次发现该网页过期";
            if (expireCommodityRecorder.reachMaxFailTimes(commodityId)) {
                commodityDao.deactivateByProgram(commodityId);
                logInfo += "，自动标记为过期网页";
            }
            crawlLogDao.logFail(commodityId, logInfo);
            return false;
        } else {
            expireCommodityRecorder.clear(commodityId);
            return true;
        }
    }
}
