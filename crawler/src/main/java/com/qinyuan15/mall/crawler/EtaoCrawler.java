package com.qinyuan15.mall.crawler;

import com.google.common.collect.Lists;
import com.qinyuan15.mall.crawler.html.CommodityPageParserBuilder;
import com.qinyuan15.mall.crawler.html.CommodityPageValidator;
import com.qinyuan15.mall.crawler.http.DatabaseProxyPool;
import com.qinyuan15.utils.concurrent.ParallelJobExecutor;
import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.IProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class to get page content of www.etao.com
 * Created by qinyuan on 15-5-14.
 */
public class EtaoCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(EtaoCrawler.class);
    public final static int DEFAULT_THREAD_SIZE = 16;
    public final static int TIMEOUT = 20000;
    private final String url;
    private final CommodityPageParserBuilder pageParserBuilder;

    public EtaoCrawler(String url, CommodityPageParserBuilder pageParserBuilder) {
        this.url = url;
        this.pageParserBuilder = pageParserBuilder;
    }

    public synchronized String getContent() {
        List<IProxy> proxies = Lists.newArrayList((IProxy) null);
        proxies.addAll(new DatabaseProxyPool().next(DEFAULT_THREAD_SIZE - 1));

        if (this.url == null) {
            return null;
        }

        ParallelJobExecutor executor = new ParallelJobExecutor(TIMEOUT);
        for (IProxy proxy : proxies) {
            executor.addJob(new CrawlThread(proxy, url));
        }
        CrawlThread thread = (CrawlThread) executor.getSuccessThread();
        return thread == null ? null : thread.content;
    }

    private class CrawlThread extends ParallelJobExecutor.ParallelThread {
        final IProxy proxy;
        final String url;
        String content;

        private CrawlThread(IProxy proxy, String url) {
            this.proxy = proxy;
            this.url = url;
        }

        private boolean isReject(String pageContent) {
            return pageContent == null || !pageContent.contains("product-detail");
        }

        @Override
        protected boolean runJob() {
            HttpClient client = new HttpClient();
            client.setProxy(proxy);

            if (isSuccess()) {
                return false;
            }

            try {
                String content = client.getContent(url);
                if (!new CommodityPageValidator().validate(
                        pageParserBuilder.build(url, content), url)) {
                    return false;
                }

                if (isSuccess()) {
                    return false;
                }

                if (!isReject(content)) {
                    this.content = content;
                    return true;
                }
            } catch (Exception e1) {
                LOGGER.error("Fail to get content of url {} with proxy {}, info: {}",
                        url, proxy, e1);
            }
            return false;
        }
    }
}
