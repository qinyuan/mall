package com.qinyuan15.mall.core.dao;

import com.qinyuan15.mall.core.article.ArticleUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ArticleUtils
 * Created by qinyuan on 15-4-14.
 */
public class ArticleUtilsTest {
    @Test
    public void testGetTitle() throws Exception {
        String article = "<p style='color:#fff;'>HelloWorld</p>\n<div>AAAAAAAAAAAAAAAAAAAA</div>";
        assertThat(ArticleUtils.getTitle(article)).isEqualTo("HelloWorld");
    }
}
