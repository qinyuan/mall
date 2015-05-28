package com.qinyuan15.mall.crawler.parameters;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Class to crawl commodity parameter
 * Created by qinyuan on 15-5-15.
 */
public class CommodityParametersCrawler extends StoptableThread {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityParametersCrawler.class);
    public final static int DEFAULT_INTERVAL = 1800;

    private final ParametersUpdater parametersUpdater;
    private final ParametersValidator parametersValidator;

    public CommodityParametersCrawler(HttpClientPool httpClientPool) {
        LOGGER.debug("init CommodityParametersCrawler");
        this.parametersUpdater = new ParametersUpdater(httpClientPool);
        this.parametersValidator = new ParametersValidator(httpClientPool);
    }

    private boolean reInitValidator = true;

    @Override
    protected void jobToRun() {
        // TODO remove this someday
        try {
            FileUtils.write(new File("/tmp/parametersCrawler.log"), DateUtils.nowString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // validate if there are commodities need to add parameters
        if (this.parametersUpdater.hasNext()) {
            this.parametersUpdater.crawlNext();
            reInitValidator = true;
            return;
        }

        // init commodities of updater
        // then validate if there are commodities need to add parameters
        this.parametersUpdater.initCommodities();
        if (this.parametersUpdater.hasNext()) {
            this.parametersUpdater.crawlNext();
            reInitValidator = true;
            return;
        }

        if (reInitValidator) {
            this.parametersValidator.initCommodities();
        }

        // validate parameters
        if (this.parametersValidator.hasNext()) {
            this.parametersValidator.crawlNext();
            reInitValidator = false;
        } else {
            ThreadUtils.sleep(DEFAULT_INTERVAL);
            reInitValidator = true;
        }
    }
}
