package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.SimpleCommodity;
import com.qinyuan15.mall.core.dao.SimpleCommodityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool to store Commodity that get from database
 * Created by qinyuan on 15-1-9.
 */
public class DatabaseCommodityPool implements CommodityPool {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseCommodityPool.class);
    private final static int PAGE_SIZE = 100;
    private int pointer = 0;
    private List<SimpleCommodity> commodities = new ArrayList<>();
    private SimpleCommodityDao dao = new SimpleCommodityDao();

    @Override
    public synchronized SimpleCommodity next() {
        if (pointer >= this.commodities.size()) {
            this.pointer = 0;
            this.commodities = dao.getInstances(PAGE_SIZE);
            LOGGER.info("reload commodity pool, commodity count: {}", this.commodities.size());
        }

        if (commodities.size() == 0) {
            return null;
        }

        SimpleCommodity commodity = this.commodities.get(pointer);
        pointer++;
        return commodity;
    }
}