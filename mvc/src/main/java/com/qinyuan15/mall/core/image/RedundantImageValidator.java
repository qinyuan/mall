package com.qinyuan15.mall.core.image;

/**
 * Interface to validate if image is redundant
 * Created by qinyuan on 15-3-18.
 */
public interface RedundantImageValidator {
    boolean isRedundant(String imagePath);
}
