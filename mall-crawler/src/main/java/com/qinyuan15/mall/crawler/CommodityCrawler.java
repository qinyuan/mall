package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.core.commodity.CommodityPool;
import com.qinyuan15.mall.core.dao.CommodityDao;
import com.qinyuan15.mall.core.dao.SimpleCommodity;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.http.SlowProxiesTester;
import com.qinyuan15.mall.crawler.parameters.CommodityParametersCrawler;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.StoptableThread;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import com.qinyuan15.utils.http.HttpClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Grub price of commodity
 * Created by qinyuan on 15-1-1.
 */
public class CommodityCrawler {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommodityCrawler.class);
    public final static int DEFAULT_THREAD_SIZE = 10;
    public final static int DEFAULT_INTERVAL = 5;

    private int threadSize = DEFAULT_THREAD_SIZE;
    private int interval = DEFAULT_INTERVAL;
    private HttpClientPool httpClientPool;
    private CommodityPool commodityPool;
    private CommodityPageParserBuilder commodityPageParserBuilder;
    private boolean debugMode;
    private ExpireCommodityRecorder expireCommodityRecorder = new ExpireCommodityRecorder();
    private CrawlTimeValidator crawlTimeValidator = new CrawlTimeValidator();
    private final List<StoptableThread> idleThreads = new ArrayList<>();
    private final List<StoptableThread> busyThreads = new ArrayList<>();

    public void init() {
        new ModeSwitchThread().start();
    }

    private void startThreads(List<StoptableThread> threads) {
        if (threads == null || threads.size() == 0) {
            return;
        }

        for (StoptableThread thread : threads) {
            thread.start();
        }
    }

    private void stopAndClearThreads(List<StoptableThread> threads) {
        if (threads == null || threads.size() == 0) {
            return;
        }

        for (StoptableThread thread : threads) {
            thread.stopSafely();
        }
        threads.clear();
    }

    private void initBusyThreads() {
        stopAndClearThreads(busyThreads);
        for (int i = 0; i < this.threadSize; i++) {
            busyThreads.add(new CrawlThread());
            LOGGER.info("create PriceHistory crawl thread " + i);
        }
        startThreads(busyThreads);
    }

    private synchronized void initIdleThreads() {
        stopAndClearThreads(idleThreads);
        int secondsInOneHour = 3600;
        idleThreads.add(new CommodityPriceSalesValidator(
                httpClientPool, commodityPageParserBuilder, crawlTimeValidator));
        idleThreads.add(new CommodityParametersCrawler(httpClientPool)
                .setMaxHeartbeatInterval(secondsInOneHour));
        idleThreads.add(new CommodityActiveValidator(
                httpClientPool, commodityPageParserBuilder)
                .setMaxHeartbeatInterval(secondsInOneHour));
        idleThreads.add(new SlowProxiesTester("www.etao.com")
                .setMaxHeartbeatInterval(secondsInOneHour));
        startThreads(idleThreads);
    }

    public void setCrawlTimeValidator(CrawlTimeValidator crawlTimeValidator) {
        if (crawlTimeValidator != null) {
            this.crawlTimeValidator = crawlTimeValidator;
        }
    }

    public void setExpireCommodityRecorder(ExpireCommodityRecorder expireCommodityRecorder) {
        this.expireCommodityRecorder = expireCommodityRecorder;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setHttpClientPool(HttpClientPool httpClientPool) {
        this.httpClientPool = httpClientPool;
    }

    public void setCommodityPageParserBuilder(CommodityPageParserBuilder commodityPageParserBuilder) {
        this.commodityPageParserBuilder = commodityPageParserBuilder;
    }

    public void setCommodityPool(CommodityPool commodityPool) {
        this.commodityPool = commodityPool;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private boolean hasBlockedIdleThread() {
        for (StoptableThread thread : idleThreads) {
            if (thread.isBlocked()) {
                return true;
            }
        }
        return false;
    }

    private class ModeSwitchThread extends Thread {
        boolean busyMode = false;

        @Override
        public void run() {
            initIdleThreads();

            while (true) {
                ThreadUtils.sleep(60);
                long unCrawlCount = new CommodityDao().getUnCrawlCount();
                if (unCrawlCount > 0 && crawlTimeValidator.inCrawlTime()) {
                    if (!busyMode) {
                        stopAndClearThreads(idleThreads);
                        initBusyThreads();
                        LOGGER.info("switch to busy mode");
                    }
                    busyMode = true;
                } else {
                    if (busyMode) {
                        stopAndClearThreads(busyThreads);
                        initIdleThreads();
                        LOGGER.info("switch to idle mode");
                    } else if (hasBlockedIdleThread()) {
                        initIdleThreads();
                        LOGGER.info("init idle threads because of blocking");
                    }
                    busyMode = false;
                }
            }
        }
    }

    private class CrawlThread extends StoptableThread {
        private SingleCommodityCrawler singleCommodityCrawler;

        public CrawlThread() {
            this.singleCommodityCrawler = new SingleCommodityCrawler(
                    httpClientPool, commodityPageParserBuilder, expireCommodityRecorder);
        }

        @Override
        protected void jobToRun() {
            if (commodityPool == null) {
                LOGGER.info("commodity pool is null, no commodity history to grub.");
                return;
            }

            ThreadUtils.sleep(interval);
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
}
