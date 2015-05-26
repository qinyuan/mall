package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.html.HtmlParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class to parse content of page in taobao
 * Created by qinyuan on 15-5-4.
 */
public class TaobaoPageParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaobaoPageParser.class);
    private String html;

    public TaobaoPageParser(String html) {
        this.html = html;
    }

    public boolean isRejected() {
        if (this.html.contains("检测到您已经登录的账户")) {
            LOGGER.info("request is rejected: \n{}", html);
            return true;
        } else {
            return false;
        }
    }

    public String getItemId() {
        HtmlParser parser = new HtmlParser(html);
        Element itemIdDiv = parser.getElement("LineZing");
        return itemIdDiv == null ? null : itemIdDiv.attr("itemid");
    }

    public String getParameters() {
        if (!StringUtils.hasText(html)) {
            LOGGER.error("html is null or empty");
            return null;
        }

        HtmlParser parser = new HtmlParser(html);
        Element ulElement = parser.getElement("J_AttrUL");
        if (ulElement == null) {
            LOGGER.error("fail to find #J_AttrUL");
            return null;
        }

        Elements liElements = ulElement.getElementsByTag("li");
        if (liElements == null) {
            LOGGER.error("fail to find #J_AttrUL li:\n{}", html);
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Element element : liElements) {
            sb.append(element.text());
            sb.append("\n");
        }
        return sb.toString();
    }
}
