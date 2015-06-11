package com.qinyuan15.mall.crawler.http;

import org.springframework.util.StringUtils;

/**
 * Class to validate page content from www.baidu.com
 * Created by qinyuan on 15-6-11.
 */
public class BaiduPageValidator implements PageValidator {
    @Override
    public boolean validate(String pageContent) {
        return StringUtils.hasText(pageContent) && pageContent.contains("百度一下");
    }
}
