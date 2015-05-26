package com.qinyuan15.mall.crawler.parameters;

import com.qinyuan15.mall.core.dao.CommodityCrawlLogDao;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.utils.http.HttpClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class to validate parameters
 * Created by qinyuan on 15-5-19.
 */
public class ParametersValidator extends AbstractParametersCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParametersValidator.class);

    public ParametersValidator(HttpClientPool httpClientPool) {
        super(httpClientPool);
    }

    @Override
    protected String getInitSQLWhereClause() {
        return "parameters IS NOT NULL AND parameters<>''";
    }

    @Override
    protected void onCrawlSuccess(Commodity commodity, String newParameters) {
        if (StringUtils.hasText(newParameters)) {
            newParameters = newParameters.trim();
            if (!newParameters.equals(commodity.parameters.trim())) {
                new CommodityDao().updateParameters(commodity.id, newParameters);
                new CommodityCrawlLogDao().logSuccess(commodity.id, "商品参数更新成功");
                /*LOGGER.warn("the old parameters is:\n{}\nbut the new parameters is\n{}",
                        commodity.parameters, newParameters);*/
            }
        } else {
            LOGGER.warn("parameters crawled is empty, commodity name: {}, commodity url: {}",
                    commodity.name, commodity.url);
        }
    }

    @Override
    protected void onCrawlFail(Commodity commodity, Exception e) {
        LOGGER.error("fail to validate commodity {}, info: {}", commodity.name, e);
    }
}
