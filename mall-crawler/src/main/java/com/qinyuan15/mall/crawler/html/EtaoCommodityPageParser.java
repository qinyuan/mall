package com.qinyuan15.mall.crawler.html;

import com.google.common.collect.Lists;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.html.HtmlParser;
import com.qinyuan15.utils.html.JavaScriptExecutor;
import com.qinyuan15.utils.http.HttpClientPool;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to parse page of http://s.etao.com
 * Created by qinyuan on 15-1-2.
 */
public class EtaoCommodityPageParser extends AbstractCommodityPageParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(EtaoCommodityPageParser.class);

    private final JavaScriptExecutor jsParser = new JavaScriptExecutor();
    private final HttpClientPool httpClientPool;

    EtaoCommodityPageParser(String html, HttpClientPool httpClientPool) {
        super(html);
        this.httpClientPool = httpClientPool;
    }

    public String getUrl() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        for (Element element : htmlParser.getElements("link")) {
            String rel = element.attr("rel");
            if (rel != null && rel.equals("canonical")) {
                return element.attr("href");
            }
        }

        for (Element element : htmlParser.getElements("div", "product-info")) {
            if (element.hasAttr("nid")) {
                return "http://s.etao.com/detail/" + element.attr("nid") + ".html";
            }
        }

        return null;
    }

    public String getName() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        Elements nameElements = htmlParser.getElements("h1", "top-title");
        for (Element nameElement : nameElements) {
            String name = nameElement.text();
            if (StringUtils.hasText(name)) {
                return name;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    private Double getPrice() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        Elements realPriceElements = htmlParser.getElements("span", "real-price-num");
        for (Element realPriceElement : realPriceElements) {
            Double realPrice = parseDouble(realPriceElement.text());
            if (realPrice != null) {
                return realPrice;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSales() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        Element productInfoDiv = htmlParser.getElement("div", "product-info");
        if (productInfoDiv == null) {
            LOGGER.warn("div.product-info is not found");
            return null;
        }

        Element ulElement = HtmlParser.getSubElement(productInfoDiv, "ul", "meta");
        if (ulElement == null) {
            LOGGER.warn("div.product-info ul.meta is not found");
            return null;
        }

        Elements liElements = ulElement.getElementsByTag("li");
        char htmlSpace = 160;
        String regex = String.format("销[\\s%c]*量\\d+", htmlSpace);
        Pattern pattern = Pattern.compile(regex);
        for (Element liElement : liElements) {
            String text = liElement.text().trim();
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return NumberUtils.toInt(matcher.group().replaceAll("\\D", ""));
            }
        }
        LOGGER.warn("sales pattern is not found");
        return null;
    }

    @Override
    public boolean isExpire() {
        return this.html.contains("您所访问的页面已经被删除") ||
                this.html.contains("您访问的内容不存在，您可尝试重新访问!");
    }

    private boolean isNoPriceHistoryPage() {
        return this.html.contains("product-right") && !this.html.contains("data_pricetrend");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Date, Double> getPriceHistory() {
        if (isNoPriceHistoryPage()) {
            Map<Date, Double> priceHistory = new HashMap<>();
            Double price = getPrice();
            if (price != null) {
                priceHistory.put(DateUtils.now(), price);
            }
            return priceHistory;
        }

        HtmlParser htmlParser = new HtmlParser(this.html);
        Elements productElements = htmlParser.getElements("div", "product");
        for (Element productElement : productElements) {
            Elements jsElements = productElement.getElementsByTag("script");
            if (jsElements.size() == 0) {
                continue;
            }

            for (Element jsElement : jsElements) {
                Map<Date, Double> priceHistory = getPriceHistoryFromJsString(jsElement.html());
                if (priceHistory != null) {
                    return priceHistory;
                }
            }
        }
        return null;
    }

    private Map<Date, Double> getPriceHistoryFromJsString(String js) {
        js = js.replace("window.", "");
        if (!js.contains("data_pricetrend")) {
            jsParser.evaluate(js);
            return null;
        }

        Object priceTrend = jsParser.evaluate(js + ";data_pricetrend");
        if (jsParser.isObject(priceTrend)) {
            Object points = jsParser.parseObject(priceTrend, "points");
            if (jsParser.isArray(points)) {
                @SuppressWarnings("unchecked")
                Iterable<Object> iterablePoints = (Iterable<Object>) points;
                return getPriceHistory(iterablePoints);
            }
        }
        return null;
    }

    @Override
    public List<String> getImageUrls() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        Element productPictureDiv = htmlParser.getElement("J_product-pic");
        if (productPictureDiv == null) {
            return new ArrayList<>();
        }

        String dataConfig = productPictureDiv.attr("data-config");
        if (StringUtils.hasText(dataConfig)) {
            String imageString = dataConfig.replaceAll("^[^\\[]*\\['", "").replaceAll("'\\][^\\]]*$", "");
            return Lists.newArrayList(imageString.split("','"));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getDetailImagesUrls() {
        HtmlParser htmlParser = new HtmlParser(this.html);
        Element productDetailDiv = htmlParser.getElements("div", "product-detail").first();
        if (productDetailDiv == null) {
            LOGGER.error("fail to parse detail image urls, div with class 'product-detail' doesn't appear");
            return new ArrayList<>();
        }

        String dataUrl = productDetailDiv.attr("data-url");
        if (!StringUtils.hasText(dataUrl)) {
            LOGGER.error("fail to parse detail image urls, div with class 'product-detail' has no data-url attribute");
            return new ArrayList<>();
        }

        return new EtaoCommodityDetailImagesParser(httpClientPool, dataUrl).getDetailImages();
    }


    private Map<Date, Double> getPriceHistory(Iterable<Object> array) {
        Map<Date, Double> map = new TreeMap<>();
        for (Object element : array) {
            if (jsParser.isArray(element)) {
                Object date = jsParser.parseArray(element, 0);
                Object price = jsParser.parseArray(element, 1);
                if (date instanceof String) {
                    if (price instanceof Double) {
                        map.put(DateUtils.newDate((String) date), (Double) price);
                    } else if (price instanceof Integer) {
                        map.put(DateUtils.newDate((String) date), ((Integer) price).doubleValue());
                    }
                }

            }
        }
        return map;
    }

    private Double parseDouble(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        str = str.replaceAll("^\\D+", "");
        return StringUtils.hasText(str) ? Double.parseDouble(str) : null;
    }
}
