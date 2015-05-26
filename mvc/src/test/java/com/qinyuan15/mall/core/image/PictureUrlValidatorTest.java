package com.qinyuan15.mall.core.image;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test PictureUrlValidator
 * Created by qinyuan on 15-3-1.
 */
public class PictureUrlValidatorTest {

    @Test
    public void testIsLocal() throws Exception {
        PictureUrlValidator validator = new PictureUrlValidator("192.168.8.11");
        assertThat(validator.isLocal("ftp://192.168.8.11/hello/world.png")).isTrue();
        assertThat(validator.isLocal("ftp://192.168.8.111/hello/world.png")).isFalse();
    }

}
