package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.PaginationItemFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Dao object of CommodityCrawlLog
 * Created by qinyuan on 15-3-29.
 */
public class CommodityCrawlLogDao {
    public Integer log(Integer commodityId, String action, Boolean success) {
        CommodityCrawlLog log = new CommodityCrawlLog();
        log.setCommodityId(commodityId);
        log.setAction(action);
        log.setLogTime(DateUtils.nowString());
        log.setSuccess(success);
        return HibernateUtils.save(log);
    }

    public Integer logSuccess(Integer commodityId, String action) {
        return this.log(commodityId, action, true);
    }

    public Integer logFail(Integer commodityId, String action) {
        return this.log(commodityId, action, false);
    }

    public static Factory factory() {
        return new Factory();
    }

    public static class Factory implements PaginationItemFactory<CommodityCrawlLog> {
        private Boolean success;
        private Integer commodityId;
        private String commodityShowId;

        public Factory setSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public Factory setCommodityId(Integer commodityId) {
            this.commodityId = commodityId;
            return this;
        }

        public Factory setCommodityShowId(String commodityShowId) {
            this.commodityShowId = commodityShowId;
            return this;
        }

        public long getCount() {
            return HibernateUtils.getCount(CommodityCrawlLog.class, getWhereClause());
        }

        private String getWhereClause() {
            String whereClause = "commodityId IN (SELECT id FROM Commodity)";
            if (this.success != null) {
                whereClause += " AND success=" + this.success;
            }
            if (IntegerUtils.isPositive(this.commodityId)) {
                whereClause += " AND commodityId=" + this.commodityShowId;
            }
            if (StringUtils.hasText(this.commodityShowId)) {
                whereClause += " AND commodityId IN (SELECT id FROM Commodity WHERE showId LIKE '%"
                        + StringEscapeUtils.escapeSql(this.commodityShowId) + "%')";
            }
            return whereClause;
        }

        public List<CommodityCrawlLog> getInstances(int firstResult, int maxResults) {
            return HibernateUtils.getList(CommodityCrawlLog.class,
                    getWhereClause() + " ORDER BY logTime DESC", firstResult, maxResults);
        }
    }
}
