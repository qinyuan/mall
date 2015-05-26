package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.html.CommodityPageParser;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.html.CommodityPageValidator;
import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.mall.core.dao.CommodityPriceDao;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.Double;import java.lang.Exception;import java.lang.Integer;import java.lang.Math;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.SuppressWarnings;import java.lang.Thread;import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to validate price and sales crawl result
 * Created by qinyuan on 15-5-13.
 */
public class CommodityPriceSalesValidator extends StoptableThread {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityPriceSalesValidator.class);
    public final static int DEFAULT_INTERVAL = 1800;

    private int pointer = 0;
    private List<Commodity> commodities;

    final private CommodityPageParserBuilder pageParserBuilder;
    final private HttpClientPool httpClientPool;
    final private CrawlTimeValidator crawlTimeValidator;

    CommodityPriceSalesValidator(HttpClientPool clientPool,
                                 CommodityPageParserBuilder pageParserBuilder,
                                 CrawlTimeValidator crawlTimeValidator) {
        this.httpClientPool = clientPool;
        this.pageParserBuilder = pageParserBuilder;
        this.crawlTimeValidator = crawlTimeValidator;
        initCommodities();
    }

    @Override
    protected void jobToRun() {
        try {
            // TODO remove this someday
            FileUtils.write(new File("/tmp/priceSalesValidator.log"), DateUtils.nowString());

            if (!this.crawlTimeValidator.inCrawlTime()) {
                Thread.sleep(60);
                return;
            }

            if (commodities == null || commodities.size() <= pointer) {
                ThreadUtils.sleep(DEFAULT_INTERVAL);
                initCommodities();
            }

            if (pointer >= commodities.size()) {
                return;
            }

            Commodity commodity = commodities.get(pointer);

            String today = DateUtils.getDatePart(DateUtils.nowString());
            if (!today.equals(commodity.crawlDate)) {
                LOGGER.warn("today is {}, buy crawlDate is {}", today, commodity.crawlDate);
                return;
            }

            LOGGER.info("validate crawl result of commodity {}({})",
                    commodity.name, commodity.url);

            String html;
            try {
                html = httpClientPool.next().getContent(commodity.url);
            } catch (Exception e) {
                LOGGER.error("fail to get web page of {}, error info: {}", commodity.url, e);
                return;
            }

            CommodityPageParser pageParser = pageParserBuilder.build(commodity.url, html);
            if (new CommodityPageValidator().validate(pageParser, commodity.url)) {
                validatePrice(commodity, pageParser.getPriceHistory());
                validateSales(commodity, pageParser.getSales());
            }
        } catch (java.lang.Throwable e) {
            LOGGER.error("error in running CommodityPriceSalesValidator: {}", e);
        } finally {
            pointer++;
        }
    }

    private void validatePrice(Commodity commodity, Map<Date, Double> priceHistory) {
        if (isPriceIncorrect(commodity, priceHistory)) {
            new CommodityPriceDao().updatePriceHistory(priceHistory, commodity.id);
            new CommodityCrawlLogDao().logSuccess(commodity.id, "价格记录更新成功");
        }
    }

    private boolean isPriceIncorrect(Commodity commodity, Map<Date, Double> priceHistory) {
        if (priceHistory == null) {
            LOGGER.warn("fail to crawl price history of {}({})", commodity.name, commodity.url);
            return true;
        }

        for (Map.Entry<Date, Double> price : priceHistory.entrySet()) {
            if (price.getKey().toString().equals(commodity.crawlDate)) {
                Double realPrice = price.getValue();
                if (realPrice == null || commodity.price == null ||
                        Math.abs(realPrice - commodity.price) >= 1.0) {
                    LOGGER.warn("price of '{}' is incorrect, the save one is {} but real one is {}",
                            commodity.name, commodity.price, realPrice);
                    return false;
                }
            }
        }
        return true;
    }

    private void validateSales(Commodity commodity, Integer realSales) {
        if (realSales == null) {
            LOGGER.warn("fail to crawl sales of {}({})", commodity.name, commodity.url);
        } else {
            if (!realSales.equals(commodity.sales)) {
                LOGGER.warn("sales of '{}' is incorrect, the save one is {} but real one is {}",
                        commodity.name, commodity.sales, realSales);
                new CommodityDao().updateSales(commodity.id, realSales);
            }
        }
    }

    private void initCommodities() {
        String hql = "SELECT " + Commodity.FIELDS +
                " FROM Commodity WHERE active=true AND crawlDate=date(now()) ";
        commodities = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<Object[]> items = HibernateUtils.getList(hql);
        for (Object[] item : items) {
            commodities.add(new Commodity(item));
        }
        pointer = 0;
        LOGGER.info("reload commodities");
    }

    private static class Commodity {
        final static String FIELDS = "id,name,url,crawlDate,price,sales";
        final Integer id;
        final String name;
        final String crawlDate;
        final String url;
        final Double price;
        final Integer sales;

        Commodity(Object[] fields) {
            id = (Integer) fields[0];
            name = (String) fields[1];
            url = (String) fields[2];
            crawlDate = DateUtils.getDatePart((String) fields[3]);
            price = (Double) fields[4];
            sales = (Integer) fields[5];
        }
    }
}
