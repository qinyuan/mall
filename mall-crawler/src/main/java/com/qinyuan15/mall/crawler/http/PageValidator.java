package com.qinyuan15.mall.crawler.http;

/**
 * Interface to validate page get by http client
 * Created by qinyuan on 15-6-11.
 */
public interface PageValidator {
    boolean validate(String pageContent);
}
