package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.concurrent.ParallelJobExecutor;
import com.qinyuan15.utils.html.HtmlParser;
import com.qinyuan15.utils.http.HttpClient;
import com.qinyuan15.utils.http.HttpClientPool;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse detail images of etao.com
 * Created by qinyuan on 15-5-15.
 */
public class EtaoCommodityDetailImagesParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(EtaoCommodityDetailImagesParser.class);
    private final HttpClientPool httpClientPool;
    private final String dataUrl;
    public final static int DEFAULT_THREAD_SIZE = 10;

    EtaoCommodityDetailImagesParser(HttpClientPool httpClientPool, String dataUrl) {
        this.httpClientPool = httpClientPool;
        this.dataUrl = dataUrl;
    }

    private HttpClient getHttpClient() {
        return this.httpClientPool == null ? new HttpClient() : this.httpClientPool.next();
    }

    public List<String> getDetailImages() {
        ParallelJobExecutor executor = new ParallelJobExecutor(15000);
        // request without proxy
        //executor.addJob(new GrubThread(new HttpClient(), dataUrl));
        for (int i = 0; i < DEFAULT_THREAD_SIZE - 1; i++) {
            executor.addJob(new GrubThread(getHttpClient(), dataUrl));
        }
        GrubThread successThread = (GrubThread) executor.getSuccessThread();
        return successThread == null ? new ArrayList<String>() : successThread.detailImageUrls;
    }

    private static class GrubThread extends ParallelJobExecutor.ParallelThread {
        List<String> detailImageUrls;
        final HttpClient httpClient;
        final String dataUrl;

        GrubThread(HttpClient httpClient, String dataUrl) {
            this.httpClient = httpClient;
            this.dataUrl = dataUrl;
        }

        @Override
        protected boolean runJob() {
            try {
                if (isSuccess()) {
                    return false;
                }
                String dataUrlContent = httpClient.getContent(dataUrl);
                if (!StringUtils.hasText(dataUrlContent)) {
                    httpClient.feedbackRejection();
                    return false;
                }

                dataUrlContent = dataUrlContent.trim();
                if (!dataUrlContent.contains("var desc")) {
                    httpClient.feedbackRejection();
                    return false;
                }

                if (isSuccess()) {
                    return false;
                }

                this.detailImageUrls = parseDetailImageUrls(dataUrlContent);
                return true;
            } catch (Exception e) {
                //LOGGER.error("fail to parse detail images, info: {}", e);
                return false;
            }
        }

        private List<String> parseDetailImageUrls(String content) {
            content = content.trim().replace("var desc='", "").replace("';", "");
            HtmlParser parser = new HtmlParser(content);
            List<String> urls = new ArrayList<>();
            for (Element element : parser.getElements("img")) {
                if (!element.hasClass("desc_anchor")) {
                    String src = element.attr("src");
                    src = src.replace(".taobaocdn.ex1.reyzar.net/", ".taobaocdn.com/");
                    urls.add(src);
                }
            }
            return urls;
        }
    }
}
