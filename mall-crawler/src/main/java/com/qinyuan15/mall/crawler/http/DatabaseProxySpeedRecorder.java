package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.IProxy;
import com.qinyuan15.utils.http.ProxySpeedRecorder;

/**
 * Record proxy speed to database
 * Created by qinyuan on 15-2-23.
 */
public class DatabaseProxySpeedRecorder implements ProxySpeedRecorder {
    @Override
    public void recordSpeed(IProxy proxy, int speed) {
        if (proxy != null && speed > 0) {
            proxy.setSpeed(speed);
            proxy.setSpeedUpdateTime(DateUtils.nowString());
            HibernateUtils.update(proxy);
        }
    }
}
