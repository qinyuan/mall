package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.html.HtmlParser;
import com.qinyuan15.utils.html.JavaScriptExecutor;
import com.qinyuan15.utils.http.IpUtils;
import com.qinyuan15.utils.http.Proxy;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse page of http://pachong.org
 * Created by qinyuan on 14-12-29.
 */
public class PachongPageParser extends AbstractProxyPageParser {

    private JavaScriptExecutor javaScriptParser = new JavaScriptExecutor();

    @Override
    public List<Proxy> getProxies() {
        List<Proxy> proxies = new ArrayList<>();

        HtmlParser parser = new HtmlParser(this.html);

        String firstJavaScriptCode = getFirstJavaScriptElement(parser).html();
        Elements elements = parser.getElements("table", "tb");
        if (elements.size() == 0) {
            return proxies;
        }

        Element tableElement = elements.get(0);
        Elements trElements = tableElement.getElementsByTag("tr");
        for (Element element : trElements) {
            Proxy proxy = parseProxyByTr(element, firstJavaScriptCode);
            if (proxy != null) {
                proxies.add(proxy);
            }
        }

        return proxies;
    }

    private Proxy parseProxyByTr(Element trElement, String firstJavaScriptCode) {
        Elements tdElements = trElement.getElementsByTag("td");
        String host = null;
        String port = null;
        final String portTdStart = "<script>document.write(";
        final String portTdEnd = ");</script>";
        for (Element element : tdElements) {
            String html = element.html().trim();
            if (IpUtils.isIPv4(html)) {
                host = html;
            }
            if (html.startsWith(portTdStart) && html.endsWith(portTdEnd)) {
                String portExpression = html.replace(portTdStart, "").replace(portTdEnd, "");
                portExpression = firstJavaScriptCode + ";" + portExpression;
                port = javaScriptParser.evaluate(firstJavaScriptCode + ";" + portExpression).toString();
                port = port.replace(".0", "");
            }
        }

        if (host != null && port != null) {
            Proxy proxy = new Proxy();
            proxy.setHost(host);
            proxy.setPort(Integer.parseInt(port));
            return proxy;
        } else {
            return null;
        }
    }

    private Element getFirstJavaScriptElement(HtmlParser parser) {
        Elements jsElements = parser.getElements("script");
        for (Element element : jsElements) {
            if (!element.hasAttr("src")) {
                return element;
            }
        }
        return null;
    }

    @Override
    public List<String> getSubLinks() {
        // TODO wait for implementing
        return new ArrayList<>();
    }
}
