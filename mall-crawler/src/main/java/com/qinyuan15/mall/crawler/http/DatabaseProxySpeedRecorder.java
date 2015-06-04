package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.*;
import com.qinyuan15.utils.mvc.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Record proxy speed to database
 * Created by qinyuan on 15-2-23.
 */
public class DatabaseProxySpeedRecorder implements ProxyRecorder {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseProxySpeedRecorder.class);

    @Override
    public void recordSpeed(IProxy proxy, int speed) {
        if (proxy != null && speed > 0) {
            proxy.setSpeed(speed);
            proxy.setSpeedUpdateTime(DateUtils.nowString());
            HibernateUtils.update(proxy);
        }
    }

    @Override
    public void recordRejection(IProxy iProxy, String url) {
        if (!(iProxy instanceof Proxy)) {
            return;
        }

        Proxy proxy = (Proxy) iProxy;
        String host = UrlUtils.getHost(url);
        if (new ProxyRejectionDao().hasInstance(proxy.getId(), host)) {
            LOGGER.info("reject of proxy {} by host {} is already recorded", proxy, host);
            return;
        }

        ProxyRejection proxyRejection = new ProxyRejection();
        proxyRejection.setHost(host);
        proxyRejection.setProxyId(proxy.getId());
        proxyRejection.setRejectTime(DateUtils.nowString());
        proxyRejection.setUrl(url);

        HibernateUtils.save(proxyRejection);
    }
}
