package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.test.TestFileUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BaiduPageValidator
 * Created by qinyuan on 15-6-11.
 */
public class BaiduPageValidatorTest {
    @Test
    public void testValidate() throws Exception {
        BaiduPageValidator validator = new BaiduPageValidator();
        String content = TestFileUtils.read("baidu.html");
        assertThat(validator.validate(content)).isTrue();
    }
}
