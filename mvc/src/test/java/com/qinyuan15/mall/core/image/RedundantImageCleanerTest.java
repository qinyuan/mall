package com.qinyuan15.mall.core.image;

import org.junit.Test;

/**
 * Test RedundantImageCleaner
 * Created by qinyuan on 15-3-18.
 */
public class RedundantImageCleanerTest {
    @Test
    public void testClean() throws Exception {
        RedundantImageCleaner cleaner = new RedundantImageCleaner();
        cleaner.setBaseDir("/var/ftp");
        cleaner.setDebugMode(true);
        cleaner.setRedundantImageValidator(new RedundantImageValidator() {
            @Override
            public boolean isRedundant(String imagePath) {
                return true;
            }
        });
        cleaner.clean();
    }
}
