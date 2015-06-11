package com.qinyuan15.mall.crawler.http;

import org.springframework.util.StringUtils;

/**
 * Class to validate page get from www.etao.com
 * Created by qinyuan on 15-6-11.
 */
public class EtaoPageValidator implements PageValidator {
    @Override
    public boolean validate(String pageContent) {
        return StringUtils.hasText(pageContent) &&
                (pageContent.contains("一淘网") || pageContent.contains("www.etao.com"));
    }
}
