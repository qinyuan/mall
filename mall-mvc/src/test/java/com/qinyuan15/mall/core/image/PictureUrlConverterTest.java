package com.qinyuan15.mall.core.image;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test PictureUrlConverter
 * Created by qinyuan on 15-3-1.
 */
public class PictureUrlConverterTest {
    private PictureUrlConverter pictureUrlConverter;

    @Before
    public void setUp() throws Exception {
        pictureUrlConverter = new PictureUrlConverter();
        pictureUrlConverter.setUrlPrefix("ftp://127.0.0.1");
        pictureUrlConverter.setPathPrefix("/var/ftp");
        pictureUrlConverter.setOtherPathPrefixes(Lists.newArrayList("/var/www/html/images"));
    }

    @Test
    public void testUrlToPath() throws Exception {
        String testUrl = "ftp://127.0.0.1/hello/World.png";
        assertThat(pictureUrlConverter.urlToPath(testUrl)).isEqualTo("/var/ftp/hello/World.png");

        testUrl = "http://127.0.0.1/hello/World.png";
        assertThat(pictureUrlConverter.urlToPath(testUrl)).isEqualTo("http://127.0.0.1/hello/World.png");

        pictureUrlConverter.setUrlPrefix("http://127.0.0.1");
        assertThat(pictureUrlConverter.urlToPath(testUrl)).isEqualTo("/var/ftp/hello/World.png");
    }

    @Test
    public void testPathToUrl() {
        String testPath = "/var/ftp/hello/world.png";
        assertThat(pictureUrlConverter.pathToUrl(testPath)).isEqualTo("ftp://127.0.0.1/hello/world.png");

        testPath = "http://hello/world.png";
        assertThat(pictureUrlConverter.pathToUrl(testPath)).isEqualTo("http://hello/world.png");
    }

    @Test
    public void testPathsToUrls() {
        List<String> testPaths = Lists.newArrayList("/var/ftp/hello", "/var/www/html/images/test");
        List<String> urls = pictureUrlConverter.pathsToUrls(testPaths);
        assertThat(urls).containsExactly("ftp://127.0.0.1/hello", "ftp://127.0.0.1/test");
    }
}
