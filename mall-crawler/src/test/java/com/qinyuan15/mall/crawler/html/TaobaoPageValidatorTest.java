package com.qinyuan15.mall.crawler.html;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test TaobaoPageValidator
 * Created by qinyuan on 15-5-18.
 */
public class TaobaoPageValidatorTest {
    @Test
    public void testValidate() throws Exception {
        String testId = RandomStringUtils.randomNumeric(10);
        TaobaoPageValidator validator = new TaobaoPageValidator();
        TaobaoPageParser pageParser = mock(TaobaoPageParser.class);
        when(pageParser.getItemId()).thenReturn(testId);

        String testUrl = "http://detail.tmall.com/item.htm?id=" + testId;
        assertThat(validator.validate(pageParser, testUrl)).isTrue();
        assertThat(validator.validate(pageParser, testUrl + "1")).isFalse();
    }
}
