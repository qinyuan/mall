package com.qinyuan15.mall.crawler.parameters;

import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.utils.http.HttpClientPool;
import com.qinyuan15.utils.http.HttpExceptionUtils;
import org.springframework.util.StringUtils;

/**
 * Class to update Parameters
 * <p>
 * Only commodities whose parameters is empty are updated
 * </p>
 * Created by qinyuan on 15-5-19.
 */
class ParametersUpdater extends AbstractParametersCrawler {
    private final CommodityCrawlLogDao crawlLogDao = new CommodityCrawlLogDao();

    ParametersUpdater(HttpClientPool httpClientPool) {
        super(httpClientPool);
    }

    @Override
    protected String getInitSQLWhereClause() {
        return "parameters IS NULL OR parameters=''";
    }

    @Override
    protected void onCrawlSuccess(AbstractParametersCrawler.Commodity commodity, String newParameters) {
        if (StringUtils.hasText(newParameters)) {
            new CommodityDao().updateParameters(commodity.id, newParameters);
            crawlLogDao.logSuccess(commodity.id, "商品参数抓取成功");
        } else {
            crawlLogDao.logSuccess(commodity.id, "商品参数抓取失败：网页解析失败");
        }
    }

    @Override
    protected void onCrawlFail(AbstractParametersCrawler.Commodity commodity, Exception e) {
        if (HttpExceptionUtils.isHttpException(e)) {
            crawlLogDao.logFail(commodity.id, "商品参数抓取失败：代理服务器或网页连接失败");
        } else {
            crawlLogDao.logFail(commodity.id, "商品参数抓取失败：未知错误");
        }
    }
}
