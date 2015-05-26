package com.qinyuan15.mall.core.config;

import org.springframework.util.StringUtils;

/**
 * Class to adapt link
 * Created by qinyuan on 15-4-1.
 */
public class LinkAdapter {
    public String adjust(String link) {
        if (!StringUtils.hasText(link)) {
            return link;
        }

        link = link.trim();
        if (link.startsWith("www.")) {
            link = "http://" + link;
        }

        return link;
    }
}
