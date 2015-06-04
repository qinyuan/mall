package com.qinyuan15.mall.crawler;

import com.qinyuan15.mall.crawler.html.TaobaoPageParser;
import com.qinyuan15.mall.crawler.html.TaobaoPageValidator;
import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.HttpClientPool;

import java.util.List;

/**
 * Crawler to crawl www.taobao.com
 * Created by qinyuan on 15-5-4.
 */
public class TaobaoCrawler {
    public final static int DEFAULT_THREAD_SIZE = 16;
    public final static int RETRY_TIMES = 10;
    private final String url;
    private final HttpClientPool httpClientPool;
    private boolean multiThreadMode;

    public TaobaoCrawler(String url, HttpClientPool httpClientPool) {
        this.url = url;
        this.httpClientPool = httpClientPool;
    }

    public void setMultiThreadMode(boolean multiThreadMode) {
        this.multiThreadMode = multiThreadMode;
    }

    public String getParameters() {
        if (this.multiThreadMode) {
            List<HttpClient> httpClients = httpClientPool.next(DEFAULT_THREAD_SIZE - 1);
            httpClients.add(new HttpClient());
            return new TaobaoParametersCrawler(url, httpClients).get();
        } else {
            for (int i = 0; i < RETRY_TIMES; i++) {
                HttpClient client = httpClientPool.next();
                TaobaoPageParser pageParser = new TaobaoPageParser(
                        client.getContent(this.url));
                if (!pageParser.isRejected()) {
                    if (!new TaobaoPageValidator().validate(pageParser, url)) {
                        continue;
                    }

                    String parameters = pageParser.getParameters();
                    if (parameters != null) {
                        return parameters;
                    }
                } else {
                    client.feedbackRejection(this.url);
                }
            }
            return null;
        }
    }
}
