package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.html.HtmlParser;
import com.qinyuan15.utils.http.IpUtils;
import com.qinyuan15.utils.http.Proxy;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse proxy in http://proxy.chnlanker.com/
 * Created by qinyuan on 15-5-17.
 */
public class ChnlankerPageParser extends AbstractProxyPageParser {
    @Override
    public List<Proxy> getProxies() {
        List<Proxy> proxies = new ArrayList<>();

        if (!StringUtils.hasText(this.html)) {
            return proxies;
        }

        HtmlParser parser = new HtmlParser(this.html);
        Elements tableElements = parser.getElements("table");
        for (Element element : tableElements) {
            proxies.addAll(getProxiesByTableElement(element));
        }

        return proxies;
    }

    private List<Proxy> getProxiesByTableElement(Element tableElement) {
        List<Proxy> proxies = new ArrayList<>();
        for (Element tableRowElement : tableElement.getElementsByTag("tr")) {
            Proxy proxy = getProxyByTableRowElement(tableRowElement);
            if (proxy != null) {
                proxies.add(proxy);
            }
        }
        return proxies;
    }

    private Proxy getProxyByTableRowElement(Element tableRowElement) {
        String text = tableRowElement.text();
        if (!StringUtils.hasText(text)) {
            return null;
        }

        String[] strings = text.split("\\s+");
        for (int i = 0; i < strings.length; i++) {
            String host = strings[i];
            if (IpUtils.isIPv4(host)) {
                for (int j = i + 1; j < strings.length; j++) {
                    String port = strings[j];
                    if (port.matches("^\\d+$")) {
                        Proxy proxy = new Proxy();
                        proxy.setHost(host);
                        proxy.setPort(Integer.parseInt(port));
                        return proxy;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getSubLinks() {
        return new ArrayList<>();
    }
}
