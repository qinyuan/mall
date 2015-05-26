package com.qinyuan15.mall.core.dao;

import com.qinyuan15.mall.core.article.ArticleUtils;
import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Persist Object of Article
 * Created by qinyuan on 15-4-13.
 */
public class Article extends PersistObject {
    public final static String DEFAULT_BACKGROUND_COLOR = "#FFFFFF";
    private String content;
    private String backgroundColor = DEFAULT_BACKGROUND_COLOR;

    public String getContent() {
        return content;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBackgroundColor(String backgroundColor) {
        if (backgroundColor != null && backgroundColor.length() == 6) {
            backgroundColor = '#' + backgroundColor;
        }
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return ArticleUtils.getTitle(content);
    }
}
