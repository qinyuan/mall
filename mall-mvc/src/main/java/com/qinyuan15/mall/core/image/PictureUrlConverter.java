package com.qinyuan15.mall.core.image;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to convert commodity picture url
 * Created by qinyuan on 15-2-24.
 */
public class PictureUrlConverter {
    private String urlPrefix;
    private String pathPrefix;
    private List<String> otherPathPrefixes;

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public PictureUrlConverter setOtherPathPrefixes(List<String> otherPathPrefixes) {
        this.otherPathPrefixes = otherPathPrefixes;
        return this;
    }

    public PictureUrlConverter setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
        return this;
    }

    public String urlToPath(String url) {
        if (!StringUtils.hasText(url) || !url.startsWith(this.urlPrefix)) {
            return url;
        }

        return url.replace(this.urlPrefix, pathPrefix);
    }

    private boolean isLocal(String path) {
        if (!StringUtils.hasText(path)) {
            return false;
        }

        if (path.startsWith(this.pathPrefix)) {
            return true;
        }

        if (this.otherPathPrefixes != null) {
            for (String otherPathPrefix : this.otherPathPrefixes) {
                if (path.startsWith(otherPathPrefix)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String pathToUrl(String path) {
        if (!isLocal(path)) {
            return path;
        }

        path = path.replace(this.pathPrefix, "");
        if (this.otherPathPrefixes != null) {
            for (String prefix : this.otherPathPrefixes) {
                path = path.replace(prefix, "");
            }
        }

        if (this.urlPrefix.endsWith("/") && path.startsWith("/")) {
            path = path.substring(1);
        }
        if (!this.urlPrefix.endsWith("/") && !path.startsWith("/")) {
            path = "/" + path;
        }

        return this.urlPrefix + path;
    }

    public List<String> pathsToUrls(List<String> paths) {
        List<String> urls = new ArrayList<>();
        for (String path : paths) {
            String url = this.pathToUrl(path);
            if (url != null) {
                urls.add(url);
            }
        }
        return urls;
    }
}
