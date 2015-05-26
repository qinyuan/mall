package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.sql.Date;
import java.util.Map;

/**
 * Query price of Commodity
 * Created by qinyuan on 15-1-22.
 */
public class CommodityPriceDao {

    public static PriceRange range(Integer commodityId) {
        return new PriceRange(commodityId);
    }

    /**
     * Class to query maximum and minimum price of certain commodity
     * during a period of time
     */
    public static class PriceRange {
        private Integer commodityId;
        private String startTime;
        private String endTime;

        private PriceRange(Integer commodityId) {
            this.commodityId = commodityId;
        }

        public PriceRange setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public PriceRange setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        private String getHQL() {
            String hql = "FROM PriceRecord WHERE commodityId=" + commodityId;
            if (startTime != null) {
                hql += " AND recordTime>='" + startTime + "'";
            }
            if (endTime != null) {
                hql += " AND recordTime<='" + endTime + "'";
            }
            return hql;
        }

        public Double getMax() {
            String hql = "SELECT MAX(price) " + getHQL();
            return (Double) HibernateUtils.getList(hql).get(0);
        }

        public Double getMin() {
            String hql = "SELECT MIN(price) " + getHQL();
            return (Double) HibernateUtils.getList(hql).get(0);
        }
    }

    public Double getMaxPrice(Integer commodityId) {
        return CommodityPriceDao.range(commodityId).getMax();
    }

    public Double getMaxPriceInThreeMonth(Integer commodityId) {
        return getThreeMonthRange(commodityId).getMax();
    }

    public Double getMinPriceInThreeMonth(Integer commodityId) {
        return getThreeMonthRange(commodityId).getMin();
    }

    private PriceRange getThreeMonthRange(Integer commodityId) {
        return CommodityPriceDao.range(commodityId)
                .setStartTime(DateUtils.threeMonthsAgo().toString())
                .setEndTime(DateUtils.nowString());
    }

    public Double getCurrentPrice(Integer commodityId) {
        String hql = "SELECT price FROM PriceRecord WHERE commodityId=" + commodityId +
                " ORDER BY recordTime DESC";
        return (Double) HibernateUtils.getFirstItem(hql);
    }

    public void updatePriceHistory(Map<Date, Double> priceHistory, int commodityId) {
        if (priceHistory == null || commodityId <= 0) {
            return;
        }

        ConcurrentPriceRecordDao priceRecordDao = new ConcurrentPriceRecordDao();
        for (Map.Entry<Date, Double> entry : priceHistory.entrySet()) {
            priceRecordDao.savePriceRecord(entry.getKey(), entry.getValue(), commodityId);
        }

        CommodityDao commodityDao = new CommodityDao();
        commodityDao.updateDiscoverTime(commodityId);
        commodityDao.updatePrice(commodityId);
        commodityDao.updateCrawlDate(commodityId);
    }
}
