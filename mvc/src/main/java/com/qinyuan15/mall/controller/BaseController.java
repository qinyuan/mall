package com.qinyuan15.mall.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qinyuan15.mall.core.config.AppConfigAdapter;
import com.qinyuan15.mall.core.dao.AppConfig;
import com.qinyuan15.mall.core.dao.AppConfigDao;
import com.qinyuan15.mall.core.dao.UserLogDao;
import com.qinyuan15.mall.core.image.PictureUrlConverter;
import com.qinyuan15.utils.security.SecuritySearcher;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;

/**
 * Base class of controller
 * Created by qinyuan on 15-2-22.
 */
@Component
public class BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected final static Map<String, String> EMPTY_MAP = new HashMap<>();

    protected final static Map<String, Object> SUCCESS = createResultMap(true, null);

    protected final static String TITLE = "title";

    protected final static String BLANK = "blank";

    protected final static String RESULT = "result";

    @Autowired
    protected SecuritySearcher securitySearcher;

    @Autowired
    protected PictureUrlConverter pictureUrlConverter;

    @Autowired
    protected HttpServletRequest request;

    protected String redirect(String page) {
        return "redirect:" + page;
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
            LOGGER.error("fail to encode {}, info: {}", str, e);
            return str;
        }
    }

    protected String addErrorInfoParameter(String url, String errorInfo) {
        if (url.contains("?")) {
            url += "&";
        } else {
            url += "?";
        }
        return url + "errorInfo=" + encode(errorInfo);
    }

    protected Integer getIntParameter(String name) {
        String value = getParameter(name);
        if (value == null) {
            return null;
        }

        try {
            return Integer.parseInt(getParameter(name));
        } catch (Exception e) {
            LOGGER.error("fail to convert '{}' to integer, info: '{}'", value, e);
            return null;
        }
    }

    protected String getParameter(String name) {
        return request.getParameter(name);
    }

    protected String getLocalAddress() {
        return request.getLocalAddr();
    }

    private final static String APP_CONFIG_KEY = "appConfig";

    private void injectAppConfig() {
        AppConfig appConfig = new AppConfigAdapter(pictureUrlConverter).adjust(new AppConfigDao().getInstance());
        request.setAttribute(APP_CONFIG_KEY, appConfig);
    }

    protected AppConfig getAppConfig() {
        Object appConfig = request.getAttribute(APP_CONFIG_KEY);
        if (appConfig == null || !(appConfig instanceof AppConfig)) {
            injectAppConfig();
        }
        return (AppConfig) request.getAttribute(APP_CONFIG_KEY);
    }

    protected void setTitle(Object title) {
        getAppConfig();
        request.setAttribute(TITLE, title);
    }

    protected String toJson(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        if (this.request.getParameter("pretty") != null) {
            builder.setPrettyPrinting();
        }

        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    protected boolean isPositive(Integer intValue) {
        return intValue != null && intValue > 0;
    }

    protected boolean isPositive(String strValue) {
        return NumberUtils.isNumber(strValue) && NumberUtils.toInt(strValue) > 0;
    }

    protected void logAction(String action, Object... args) {
        Integer userId = securitySearcher.getUserId();
        if (args.length > 0) {
            action = String.format(action, args);
        }
        if (isPositive(userId)) {
            UserLogDao userLogDao = new UserLogDao();
            userLogDao.save(userId, action);
        }
    }

    protected void addCss(String file) {
        addListAttribute("moreCss", file);
    }

    protected void addJs(String file) {
        addListAttribute("moreJs", file);
    }

    protected void addHeadJs(String file) {
        addListAttribute("headJs", file);
    }

    protected void addIEJs(String file) {
        addListAttribute("ieJs", file);
    }

    protected void addCssAndJs(String file) {
        addCss(file);
        addJs(file);
    }

    @SuppressWarnings("unchecked")
    protected void addListAttribute(String key, String value) {
        if (request.getAttribute(key) == null) {
            request.setAttribute(key, new ArrayList<String>());
        }
        ((List) request.getAttribute(key)).add(value);
    }

    protected void debugParameters() {
        @SuppressWarnings("unchecked")
        Map<String, String[]> map = request.getParameterMap();
        String result = "";
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            result += "\n" + entry.getKey() + ": " + Arrays.toString(entry.getValue());
        }
        LOGGER.info(result);
    }

    protected Map<String, Object> createSimpleMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    protected static Map<String, Object> createResultMap(boolean success, Object detail) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("detail", detail);
        return map;
    }

    protected static Map<String, Object> createFailResult(Object detail) {
        return createResultMap(false, detail);
    }

    protected boolean isDifferent(Object str1, Object str2) {
        if (str1 == null) {
            return str2 != null;
        } else {
            return !str1.equals(str2);
        }
    }
}
