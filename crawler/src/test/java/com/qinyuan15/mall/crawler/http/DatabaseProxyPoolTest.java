package com.qinyuan15.mall.crawler.http;

import com.qinyuan15.utils.http.Proxy;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test DatabaseProxyPool
 * Created by qinyuan on 14-12-31.
 */
public class DatabaseProxyPoolTest {

    private DatabaseProxyPool pool;

    @Before
    public void setUp() throws Exception {
        pool = new DatabaseProxyPool();
    }

    @Test
    public void testNext() throws Exception {
        for (int i = 0; i < 100; i++) {
            Proxy proxy = pool.next();
            assertThat(proxy).isNotNull();
            assertThat(proxy.getSpeed()).isNotNull();
            System.out.println(proxy.getSpeed());
        }
    }
}
