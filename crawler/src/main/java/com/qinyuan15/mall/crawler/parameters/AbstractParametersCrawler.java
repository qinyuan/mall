package com.qinyuan15.mall.crawler.parameters;

import com.qinyuan15.mall.core.commodity.CommodityBuyUrlBuilder;
import com.qinyuan15.mall.crawler.TaobaoCrawler;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class of ParametersUpdater and ParametersValidator
 * Created by qinyuan on 15-5-19.
 */
abstract class AbstractParametersCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractParametersCrawler.class);
    private List<Commodity> commodities;
    private int pointer = 0;
    protected final HttpClientPool httpClientPool;

    AbstractParametersCrawler(HttpClientPool httpClientPool) {
        this.httpClientPool = httpClientPool;
    }

    protected abstract String getInitSQLWhereClause();

    final void initCommodities() {
        String hql = "SELECT " + Commodity.FIELDS + " FROM Commodity WHERE " + getInitSQLWhereClause()
                + " ORDER BY rand()";
        this.commodities = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<Object[]> items = HibernateUtils.getList(hql);
        for (Object[] item : items) {
            Commodity commodity = new Commodity(item);
            this.commodities.add(commodity);
        }
        this.pointer = 0;
        LOGGER.info("reload commodities without parameters");
    }

    boolean hasNext() {
        return commodities != null && pointer < commodities.size();
    }

    protected abstract void onCrawlSuccess(Commodity commodity, String newParameters);

    protected abstract void onCrawlFail(Commodity commodity, Exception e);

    void crawlNext() {
        if (!hasNext()) {
            LOGGER.error("no commodity to deal with");
        }

        try {
            Commodity commodity = commodities.get(pointer);
            String buyUrl = new CommodityBuyUrlBuilder().getBuyUrl(commodity.url);
            try {
                LOGGER.info("crawl parameters of commodity {}, crawlUrl: {}, buyUrl: {}",
                        commodity.name, commodity.url, buyUrl);
                String parameters = new TaobaoCrawler(buyUrl, httpClientPool).getParameters();
                onCrawlSuccess(commodity, parameters);
            } catch (Exception e) {
                LOGGER.error("fail to parse commodity parameters of {}, crawlUrl: {}, buyUrl: {}, info: {}",
                        commodity.name, commodity.url, buyUrl, e);
                onCrawlFail(commodity, e);
            }
        } finally {
            pointer++;
        }
    }

    static class Commodity {
        final static String FIELDS = "id,name,url,parameters";
        final Integer id;
        final String name;
        final String url;
        final String parameters;

        Commodity(Object[] fields) {
            id = (Integer) fields[0];
            name = (String) fields[1];
            url = (String) fields[2];
            parameters = (String) fields[3];
        }
    }
}
