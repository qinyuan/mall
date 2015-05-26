package com.qinyuan15.mall.ui;

import com.qinyuan15.mall.core.dao.SeoKeyword;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test SeoKeywordUtils
 * Created by qinyuan on 15-4-13.
 */
public class SeoKeywordUtilsTest {
    @Test
    public void testGetKeyword() throws Exception {
        String testPage = "list?id=7";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(testPage);
        SeoKeyword seoKeyword = SeoKeywordUtils.getMatchSeoKeyword(request);
        if (seoKeyword != null) {
            System.out.println(seoKeyword.getUrl());
            System.out.println(seoKeyword.getWord());
            System.out.println(seoKeyword.getDescription());
        }
    }

    @Test
    public void test() throws Exception {

    }


}
