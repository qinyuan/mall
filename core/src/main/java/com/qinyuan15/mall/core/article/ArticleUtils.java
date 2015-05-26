package com.qinyuan15.mall.core.article;

import org.springframework.util.StringUtils;import java.lang.String;

/**
 * Utility class of article
 * Created by qinyuan on 15-4-14.
 */
public class ArticleUtils {
    private ArticleUtils() {
    }

    public static String getTitle(String content) {
        if (!StringUtils.hasText(content)) {
            return null;
        }

        return content.split("\n")[0].replaceAll("<.*?>", "").trim();
    }
}
