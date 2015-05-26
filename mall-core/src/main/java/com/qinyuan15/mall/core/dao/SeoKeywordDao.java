package com.qinyuan15.mall.core.dao;

import org.apache.commons.lang.StringEscapeUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

/**
 * Dao of SeoKeyword
 * Created by qinyuan on 15-4-12.
 */
public class SeoKeywordDao {
    public List<SeoKeyword> getInstances() {
        return HibernateUtils.getList(SeoKeyword.class);
    }

    public SeoKeyword getInstance(Integer id) {
        return HibernateUtils.get(SeoKeyword.class, id);
    }

    public boolean hasInstance(String url) {
        return HibernateUtils.getCount(SeoKeyword.class, "url='" +
                StringEscapeUtils.escapeSql(url) + "'") > 0;
    }

    public void delete(Integer id) {
        HibernateUtils.delete(SeoKeyword.class, id);
    }

    public void add(String url, String word, String description) {
        HibernateUtils.save(setAttrs(new SeoKeyword(), url, word, description));
    }

    public void update(Integer id, String url, String word, String description) {
        SeoKeyword seoKeyword = getInstance(id);
        if (seoKeyword != null) {
            HibernateUtils.update(setAttrs(seoKeyword, url, word, description));
        }
    }

    private SeoKeyword setAttrs(SeoKeyword seoKeyword, String url, String word, String description) {
        seoKeyword.setUrl(url);
        seoKeyword.setWord(word);
        seoKeyword.setDescription(description);
        return seoKeyword;
    }
}
