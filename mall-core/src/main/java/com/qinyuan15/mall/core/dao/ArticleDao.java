package com.qinyuan15.mall.core.dao;


import com.qinyuan15.mall.core.article.ArticleUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

/**
 * Dao of AppConfig
 * Created by qinyuan on 15-4-14.
 */
public class ArticleDao {
    public List<Article> getInstances() {
        return HibernateUtils.getList(Article.class);
    }

    public String getTitleById(Integer id) {
        Article article = getInstance(id);
        return article == null ? null : ArticleUtils.getTitle(article.getContent());
    }

    public Article getInstance(Integer id) {
        return HibernateUtils.get(Article.class, id);
    }

    public void add(String content, String backgroundColor) {
        Article article = new Article();
        article.setContent(content);
        article.setBackgroundColor(backgroundColor);
        HibernateUtils.save(article);
    }

    public void update(Integer id, String content, String backgroundColor) {
        Article article = getInstance(id);
        article.setContent(content);
        article.setBackgroundColor(backgroundColor);
        HibernateUtils.update(article);
    }

    public void delete(Integer id) {
        HibernateUtils.delete(Article.class, id);
    }
}
