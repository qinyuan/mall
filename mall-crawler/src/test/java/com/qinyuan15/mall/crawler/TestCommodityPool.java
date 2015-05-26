package com.qinyuan15.mall.crawler;

import com.google.common.collect.Lists;
import com.qinyuan15.mall.core.commodity.CommodityPool;
import com.qinyuan15.mall.core.dao.Commodity;

import java.lang.Override;import java.util.List;

/**
 * A CommodityPool for testing
 * Created by qinyuan on 15-1-2.
 */
public class TestCommodityPool implements CommodityPool {

    private int pointer = 0;

    private List<Commodity> commodities = getCommodities();

    private List<Commodity> getCommodities() {
        Commodity commodity = new Commodity();
        commodity.setId(1);
        commodity.setName("testCommodity");
        commodity.setUrl("http://s.etao.com/detail/40780735321.html?tbpm=20150102");
        return Lists.newArrayList(commodity);
    }

    @Override
    public Commodity next() {
        if (this.pointer >= this.commodities.size()) {
            return null;
        }
        return this.commodities.get(this.pointer++);
    }

    //@Override
    //public void reset() {
    //    this.pointer = 0;
    //}
}
