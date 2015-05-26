package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.html.CommodityPageParser;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.html.CommodityPageValidator;
import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.Exception;import java.lang.Integer;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.Throwable;import java.util.ArrayList;
import java.util.List;

/**
 * Class to validate if commodity is active
 * Created by qinyuan on 15-5-13.
 */
public class CommodityActiveValidator extends StoptableThread {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityActiveValidator.class);
    public final static int DEFAULT_INTERVAL = 1800;

    private int pointer = 0;
    private List<Commodity> commodities;

    final private CommodityPageParserBuilder pageParserBuilder;
    final private HttpClientPool httpClientPool;

    CommodityActiveValidator(HttpClientPool clientPool,
                             CommodityPageParserBuilder pageParserBuilder) {
        this.httpClientPool = clientPool;
        this.pageParserBuilder = pageParserBuilder;
        initCommodities();
    }

    @Override
    protected void jobToRun() {
        try {
            // TODO remove this someday
            FileUtils.write(new File("/tmp/activeValidator.log"), DateUtils.nowString());

            if (commodities == null || commodities.size() <= pointer) {
                ThreadUtils.sleep(DEFAULT_INTERVAL);
                initCommodities();
            }

            if (pointer >= commodities.size()) {
                return;
            }

            Commodity commodity = commodities.get(pointer);

            LOGGER.info("validate active status of commodity {}({})",
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
                new CommodityDao().activate(commodity.id);
                new CommodityCrawlLogDao().logSuccess(commodity.id, "重新激活商品");
            }
        } catch (Throwable e) {
            LOGGER.error("error in running CommodityPriceSalesValidator: {}", e);
        } finally {
            pointer++;
        }
    }

    private void initCommodities() {
        String hql = "SELECT " + Commodity.FIELDS +
                " FROM Commodity WHERE active=false AND deactivateByProgram=true";
        commodities = new ArrayList<>();

        @java.lang.SuppressWarnings("unchecked")
        List<Object[]> items = HibernateUtils.getList(hql);
        for (Object[] item : items) {
            commodities.add(new Commodity(item));
        }
        pointer = 0;
        LOGGER.info("reload commodities");
    }

    private static class Commodity {
        final static String FIELDS = "id,name,url";
        final Integer id;
        final String name;
        final String url;

        Commodity(Object[] fields) {
            id = (Integer) fields[0];
            name = (String) fields[1];
            url = (String) fields[2];
        }
    }
}
