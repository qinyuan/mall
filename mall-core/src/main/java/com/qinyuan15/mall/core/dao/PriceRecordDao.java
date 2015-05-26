package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.sql.Date;
import java.util.List;

/**
 * Dao of PriceRecord
 * Created by qinyuan on 15-1-13.
 */
public class PriceRecordDao {

    public static Factory factory() {
        return new Factory();
    }

    public static class Factory {
        private Integer commodityId;
        private Date recordTime;
        private String startTime;
        private String endTime;
        private String grabDate;
        private Double maxPrice;

        public Factory setCommodityId(Integer commodityId) {
            this.commodityId = commodityId;
            return this;
        }

        public Factory setMaxPrice(Double maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Factory setRecordTime(Date recordTime) {
            this.recordTime = recordTime;
            return this;
        }

        public Factory setStartTime(String startTime) {
            if (DateUtils.isDateOrDateTime(startTime)) {
                this.startTime = startTime;
            }
            return this;
        }

        public Factory setEndTime(String endTime) {
            if (DateUtils.isDateOrDateTime(endTime)) {
                this.endTime = endTime;
            }
            return this;
        }

        public Factory setGrabDate(String grabDate) {
            if (DateUtils.isDateOrDateTime(grabDate)) {
                this.grabDate = grabDate;
            }
            return this;
        }

        private String getHQL() {
            // build SQL query command
            String hql = "FROM PriceRecord WHERE 1=1";
            if (recordTime != null) {
                hql += " AND recordTime='" + recordTime + "'";
            }
            if (commodityId != null && commodityId > 0) {
                hql += " AND commodityId=" + commodityId;
            }
            if (startTime != null) {
                hql += " AND recordTime>='" + startTime + "'";
            }
            if (endTime != null) {
                hql += " AND recordTime<='" + endTime + "'";
            }
            if (grabDate != null) {
                hql += " AND DATE(grab_time)='" + grabDate + "'";
            }
            if (maxPrice != null && maxPrice > 0) {
                hql += " AND price<" + maxPrice;
            }
            hql += " GROUP BY commodityId,recordTime";
            return hql;
        }

        @SuppressWarnings("unchecked")
        public List<PriceRecord> getInstances() {
            return HibernateUtils.getList(getHQL() + ASC_ORDER);
        }

        private final static String DESC_ORDER = " ORDER BY recordTime DESC";
        private final static String ASC_ORDER = " ORDER BY recordTime ASC";

        public PriceRecord getLastInstance() {
            String hql = getHQL() + DESC_ORDER;
            return (PriceRecord) HibernateUtils.getFirstItem(hql);
        }

        public PriceRecord getFirstInstance() {
            String hql = getHQL() + ASC_ORDER;
            return (PriceRecord) HibernateUtils.getFirstItem(hql);
        }

        public boolean hasInstance() {
            return getInstances().size() > 0;
        }
    }

    /**
     * Check whether today's price of a commodity is recorded
     *
     * @param commodityId id to commodity
     * @return if today's price of commodity is record, then return true
     */
    public boolean hasInstanceToday(int commodityId) {
        return factory().setCommodityId(commodityId)
                .setStartTime(DateUtils.todayStartTime())
                .setEndTime(DateUtils.todayEndTime())
                .hasInstance();
    }


    public boolean hasInstanceInThreeDay(int commodityId) {
        return factory().setCommodityId(commodityId)
                .setStartTime(DateUtils.threeDaysAgo().toString())
                .setEndTime(DateUtils.nowString())
                .hasInstance();
    }

    public void deleteByCommodityId(int commodityId) {
        HibernateUtils.delete(PriceRecord.class, "commodityId=" + commodityId);
    }

    public PriceRecord getLastInstance(int commodityId) {
        return factory().setCommodityId(commodityId).getLastInstance();
    }

    public PriceRecord getFirstInstance(int commodityId) {
        return factory().setCommodityId(commodityId).getFirstInstance();
    }
}
