package com.qinyuan15.mall.core.image;

/**
 * Validate picture url
 * Created by qinyuan on 15-3-1.
 */
public class PictureUrlValidator {
    private String localAddress;

    public PictureUrlValidator(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalAddress() {
        return this.localAddress;
    }

    public boolean isLocal(String url) {
        return url.contains("://" + this.localAddress);
    }
}
