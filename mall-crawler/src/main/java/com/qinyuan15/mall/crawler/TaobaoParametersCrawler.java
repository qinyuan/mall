package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.html.TaobaoPageParser;
import com.qinyuan15.mall.crawler.html.TaobaoPageValidator;
import com.qinyuan15.utils.concurrent.ParallelJobExecutor;
import com.qinyuan15.utils.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * class to crawl parameters information of taobao.com
 * Created by qinyuan on 15-5-12.
 */
class TaobaoParametersCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaobaoParametersCrawler.class);
    private final static int TIMEOUT = 20000;
    private String url;
    private List<HttpClient> httpClients;

    TaobaoParametersCrawler(String url, List<HttpClient> httpClients) {
        this.url = url;
        this.httpClients = httpClients;
    }

    public synchronized String get() {
        if (this.url == null || this.httpClients == null || this.httpClients.size() == 0) {
            return null;
        }

        ParallelJobExecutor executor = new ParallelJobExecutor(TIMEOUT);
        for (HttpClient httpClient : this.httpClients) {
            executor.addJob(new ParameterCrawlThread(httpClient, url));
        }
        ParameterCrawlThread successThread = (ParameterCrawlThread) executor.getSuccessThread();
        return successThread == null ? null : successThread.parameter;
    }

    private static class ParameterCrawlThread extends ParallelJobExecutor.ParallelThread {
        String parameter;
        final HttpClient httpClient;
        final String url;

        ParameterCrawlThread(HttpClient httpClient, String url) {
            this.httpClient = httpClient;
            this.url = url;
        }

        @Override
        protected boolean runJob() {
            if (isSuccess()) {
                return false;
            }

            try {
                TaobaoPageParser pageParser = new TaobaoPageParser(httpClient.getContent(this.url));
                if (!pageParser.isRejected()) {
                    if (!new TaobaoPageValidator().validate(pageParser, url)) {
                        return false;
                    }

                    if (isSuccess()) {
                        return false;
                    }

                    String parameters = pageParser.getParameters();
                    if (isSuccess()) {
                        return false;
                    }

                    if (parameters != null) {
                        this.parameter = parameters;
                        return true;
                    }
                } else {
                    httpClient.feedbackRejection();
                }
            } catch (Exception e) {
                LOGGER.warn("fail to parse parameters of url {}, info: {}", url, e);
            }
            return false;
        }
    }
}
