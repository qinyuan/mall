package com.qinyuan15.mall.crawler.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate taobao page
 * Created by qinyuan on 15-5-18.
 */
public class TaobaoPageValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaobaoPageParser.class);

    public boolean validate(TaobaoPageParser pageParser, String url) {
        if (!StringUtils.hasText(url)) {
            LOGGER.error("invalid url: {}", url);
            return false;
        }

        String itemId = pageParser.getItemId();
        if (!StringUtils.hasText(itemId)) {
            LOGGER.error("invalid itemId: {}", itemId);
            return false;
        }

        Pattern pattern = Pattern.compile("(?<=id=)\\d+");
        Matcher matcher = pattern.matcher(url);
        return matcher.find() && matcher.group().equals(itemId);
    }
}
