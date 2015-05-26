package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.SimpleCommodity;

/**
 * Pool to store Commodity objects
 * Created by qinyuan on 15-1-2.
 */
public interface CommodityPool {
    SimpleCommodity next();
}
