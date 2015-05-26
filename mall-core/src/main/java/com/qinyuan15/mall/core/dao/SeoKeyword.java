package com.qinyuan15.mall.core.dao;
import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Persist object of seo keyword
 * Created by qinyuan on 15-4-12.
 */
public class SeoKeyword extends PersistObject {
    private String url;
    private String word;
    private String description;

    public String getUrl() {
        return url;
    }

    public String getWord() {
        return word;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
