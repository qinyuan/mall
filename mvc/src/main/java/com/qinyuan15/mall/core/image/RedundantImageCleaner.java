package com.qinyuan15.mall.core.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Class to delete redundant images
 * Created by qinyuan on 15-3-18.
 */
public class RedundantImageCleaner {
    private final static Logger LOGGER = LoggerFactory.getLogger(RedundantImageCleaner.class);

    private String baseDir;
    private RedundantImageValidator redundantImageValidator;
    private boolean debugMode;

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setRedundantImageValidator(RedundantImageValidator redundantImageValidator) {
        this.redundantImageValidator = redundantImageValidator;
    }

    public void clean() {
        if (this.baseDir == null) {
            LOGGER.error("baseDir is not set, give up cleaning redundant images");
        }

        File root = new File(this.baseDir);
        if (!root.isDirectory()) {
            LOGGER.error("invalid baseDir {}, give up cleaning redundant images", this.baseDir);
        }

        if (this.redundantImageValidator == null) {
            LOGGER.error("redundantImageValidator is not set, give up cleaning redundant images");
        }

        LOGGER.info("Prepare to clean directory {}", root.getAbsolutePath());
        cleanDirectory(root);
        LOGGER.info("Finish cleaning directory {}", root.getAbsolutePath());
    }

    private void cleanDirectory(File dir) {
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            for (File file : subFiles) {
                if (file.isDirectory()) {
                    this.cleanDirectory(file);
                } else {
                    if (this.redundantImageValidator.isRedundant(file.getAbsolutePath())) {
                        cleanFile(file);
                    }
                }
            }
        }
    }

    private void cleanFile(File file) {
        if (this.debugMode) {
            LOGGER.info("clean redundant image {}", file.getAbsolutePath());
        } else {
            if (file.delete()) {
                LOGGER.info("clean redundant image {}", file.getAbsolutePath());
            } else {
                LOGGER.error("fail to clean redundant image {}", file.getAbsolutePath());
            }
        }
    }
}
