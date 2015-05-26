package com.qinyuan15.mall.controller;

import com.google.common.collect.Lists;
import com.qinyuan15.mall.core.commodity.SnapshotConfig;
import com.qinyuan15.mall.core.image.PictureUrlConverter;
import com.qinyuan15.utils.image.ImageDownloader;
import org.powermock.reflect.Whitebox;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tool class for controller test case
 * Created by qinyuan on 15-2-17.
 */
public class ControllerTestUtils {
    public static void injectRequest(BaseController controller) throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getLocalAddr()).thenReturn("192.168.8.1");
        when(request.getParameter("pretty")).thenReturn("true");
        inject(controller, "request", request);
    }

    public static void injectPictureUrlConvertor(ImageController controller) throws Exception {
        PictureUrlConverter urlConverter = new PictureUrlConverter();
        urlConverter.setUrlPrefix("http://127.0.0.1/images");
        urlConverter.setPathPrefix("/var/www/html/images");
        urlConverter.setOtherPathPrefixes(Lists.newArrayList("/var/ftp"));
        inject(controller, "pictureUrlConverter", urlConverter);
    }

    public static void injectImageDownloader(ImageController controller) throws Exception {
        ImageDownloader imageDownloader = new ImageDownloader("/var/ftp");
        inject(controller, "imageDownloader", imageDownloader);
    }

    public static void injectSnapshotConfig(ImageController controller) throws Exception {
        SnapshotConfig snapshotConfig = new SnapshotConfig();
        snapshotConfig.setLoadSize(3);
        inject(controller, "snapshotConfig", snapshotConfig);
    }

    public static void inject(BaseController controller, String fieldName, Object value) throws Exception {
        Whitebox.getField(controller.getClass(), fieldName).set(controller, value);
    }

    public static ModelMap mockModelMap() {
        return new ModelMap();
    }
}

