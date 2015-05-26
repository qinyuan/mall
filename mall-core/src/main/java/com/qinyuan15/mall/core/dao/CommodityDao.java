package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.PaginationItemFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao of Commodity
 * Created by qinyuan on 15-1-14.
 */
public class CommodityDao {
    private final static double LOW_PRICE_DEVIATION = 1.0;

    public boolean hasDuplicateShowId(Integer id, String showId) {
        List<Commodity> commodities = new CommodityDao().getInstancesByShowId(showId);
        if (IntegerUtils.isPositive(id)) {
            for (Commodity commodity : commodities) {
                if (!commodity.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        } else {
            return commodities.size() > 0;
        }
    }

    public long getCount() {
        return factory().getCount();
    }

    public long getActiveCount() {
        return factory().setActive(true).getCount();
    }

    public long getUnCrawlCount() {
        return getActiveCount() - getCrawlCount();
    }

    public long getCrawlCount() {
        return factory().setCrawlDate(DateUtils.now()).getCount();
    }

    /**
     * get the number of commodity with parameters
     *
     * @return number of commodity with parameters
     */
    public long getWithParametersCount() {
        return factory().setWithParameters(true).getCount();
    }

    public Commodity getInstance(int id) {
        List<Commodity> commodities = factory().setId(id).getInstances();
        return commodities.size() == 0 ? null : commodities.get(0);
    }

    public String getNameById(Integer id) {
        Commodity commodity = getInstance(id);
        return commodity == null ? null : commodity.getName();
    }

    public Integer getIdBySerialNumber(String serialNumber) {
        Commodity commodity = HibernateUtils.getFirstItem(Commodity.class,
                "serialNumber='" + StringEscapeUtils.escapeSql(serialNumber) + "'");
        return commodity == null ? null : commodity.getId();
    }

    public Integer getIdByShowId(String showId) {
        Commodity commodity = HibernateUtils.getFirstItem(Commodity.class,
                "showId='" + StringEscapeUtils.escapeSql(showId) + "'");
        return commodity == null ? null : commodity.getId();
    }

    public List<Commodity> getInstancesByShowId(String showId) {
        return HibernateUtils.getList(Commodity.class,
                "showId='" + StringEscapeUtils.escapeSql(showId) + "'");
    }

    public void unlinkCategory(Integer categoryId) {
        String hql = "UPDATE Commodity SET categoryId=null,active=false WHERE categoryId="
                + categoryId;
        HibernateUtils.executeUpdate(hql);

        updateInLowPrice(factory().setCategoryId(categoryId).getInstances());
    }

    public void unlinkBranch(Integer branchId) {
        String hql = "UPDATE Commodity SET branchId=null,active=false WHERE branchId="
                + branchId;
        HibernateUtils.executeUpdate(hql);
        updateInLowPrice(factory().setBranchId(branchId).getInstances());
    }

    public void delete(int id) {
        HibernateUtils.delete(Commodity.class, id);
        new PriceRecordDao().deleteByCommodityId(id);
    }

    public void deactivateByProgram(int id) {
        String hql = "UPDATE Commodity SET active=false,deactivateByProgram=true WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void deactivateByUser(int id) {
        String hql = "UPDATE Commodity SET active=false,deactivateByProgram=false WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void activate(int id) {
        String hql = "UPDATE Commodity SET active=true WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void updateSales(int id, Integer sales) {
        if (sales == null) {
            return;
        }

        String hql = "UPDATE Commodity SET sales=" + sales + " WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void updatePrice(int id) {
        Double currentPrice = new CommodityPriceDao().getCurrentPrice(id);

        String hql = "UPDATE Commodity SET price=" + currentPrice + " WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void updateInLowPrice(int id) {
        updateInLowPrice(id, isInLowPrice(id));
    }

    public void updateCrawlDate(int id) {
        String today = DateUtils.now().toString();
        String hql = "UPDATE Commodity SET crawlDate='" + today + "' WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void updateParameters(int id, String parameters) {
        Commodity commodity = getInstance(id);
        commodity.setParameters(parameters);
        HibernateUtils.update(commodity);
    }

    private boolean isInLowPrice(int id) {
        if (!new PriceRecordDao().hasInstanceToday(id)) {
            return false;
        }

        CommodityPriceDao commodityPriceDao = new CommodityPriceDao();
        Double lowPrice = commodityPriceDao.getMinPriceInThreeMonth(id);
        if (lowPrice == null) {
            return false;
        }

        Double highPrice = commodityPriceDao.getMaxPriceInThreeMonth(id);
        if (highPrice == null) {
            return false;
        }

        if (highPrice - lowPrice < LOW_PRICE_DEVIATION) {
            return false;
        }

        Double currentPrice = commodityPriceDao.getCurrentPrice(id);
        return currentPrice != null && currentPrice - lowPrice < LOW_PRICE_DEVIATION;

    }

    private void updateInLowPrice(List<Commodity> commodities) {
        for (Commodity commodity : commodities) {
            if (commodity != null) {
                updateInLowPrice(commodity.getId());
            }
        }
    }

    private void updateInLowPrice(int id, boolean inLowPrice) {
        String hql = "UPDATE Commodity SET inLowPrice=" + inLowPrice + " WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void updateDiscoverTime(int id) {
        Double lowPrice = new CommodityPriceDao().getMinPriceInThreeMonth(id);
        if (lowPrice == null) {
            return;
        }

        List<PriceRecord> priceRecords = PriceRecordDao.factory()
                .setCommodityId(id)
                .setStartTime(DateUtils.threeMonthsAgo().toString())
                .setEndTime(DateUtils.nowString())
                .getInstances();
        int lastInLowIndex = getLastInLowIndex(priceRecords, lowPrice);
        if (lastInLowIndex < 0) {
            return;
        }

        int lastNotInLowIndex = getLastNotInLowIndex(
                priceRecords.subList(0, lastInLowIndex), lowPrice);
        if (lastNotInLowIndex >= 0) {
            updateDiscoverTime(id, priceRecords.get(lastNotInLowIndex + 1).getRecordTime());
        }
    }

    public void updateDiscoverTime(int id, Date discoverTime) {
        String hql = "UPDATE Commodity SET discoverTime='" + DateUtils.toLongString(discoverTime)
                + "' WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    private int getLastNotInLowIndex(List<PriceRecord> priceRecords, double lowPrice) {
        for (int i = priceRecords.size() - 1; i >= 0; i--) {
            if (priceRecords.get(i).getPrice() - lowPrice >= LOW_PRICE_DEVIATION) {
                return i;
            }
        }
        return -1;
    }

    private int getLastInLowIndex(List<PriceRecord> priceRecords, double lowPrice) {
        for (int i = priceRecords.size() - 1; i >= 0; i--) {
            if (priceRecords.get(i).getPrice() - lowPrice < LOW_PRICE_DEVIATION) {
                return i;
            }
        }
        return -1;
    }

    public static Factory factory() {
        return new Factory();
    }

    public static enum OrderField {
        ON_SHELF_TIME, PRICE, SALES;

        public static OrderField create(String str) {
            str = str.toLowerCase();
            switch (str) {
                case "price":
                    return PRICE;
                case "sales":
                    return SALES;
                default:
                    return ON_SHELF_TIME;
            }
        }
    }

    public static enum OrderType {
        ASC, DESC;

        public static OrderType create(String str) {
            str = str.toLowerCase();
            if (str.equals("asc")) {
                return ASC;
            } else {
                return DESC;
            }
        }
    }

    public static class Order {
        private OrderField field = OrderField.ON_SHELF_TIME;
        private OrderType type = OrderType.DESC;

        public Order setField(OrderField field) {
            this.field = field;
            return this;
        }

        public Order setType(OrderType type) {
            this.type = type;
            return this;
        }

        @Override
        public String toString() {
            String str;
            switch (this.field) {
                case ON_SHELF_TIME:
                    str = "discoverTime";
                    break;
                case PRICE:
                    str = "price";
                    break;
                case SALES:
                    str = "sales";
                    break;
                default:
                    str = "discoverTime";
            }
            switch (this.type) {
                case ASC:
                    str += " ASC";
                    break;
                case DESC:
                    str += " DESC";
                    break;
                default:
                    str += " ASC";
            }
            return str;
        }
    }

    public static class Factory implements PaginationItemFactory<Commodity> {
        private Integer id;
        private boolean inLowPrice = false;
        private Integer categoryId;
        private Boolean active;
        private Integer userId;
        private Integer branchId;
        private String[] keyWords;
        private Order order = new Order();
        private Date crawlDate;
        private Boolean withParameters;

        public Factory setCrawlDate(Date crawlDate) {
            this.crawlDate = crawlDate;
            return this;
        }

        public Factory setWithParameters(Boolean withParameters) {
            this.withParameters = withParameters;
            return this;
        }

        public Factory setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Factory setKeyWord(String keyWord) {
            if (StringUtils.isBlank(keyWord)) {
                this.keyWords = null;
            } else {
                this.keyWords = keyWord.split("\\s+");
            }

            return this;
        }

        public Factory setId(Integer id) {
            this.id = id;
            return this;
        }

        public Factory setActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Factory setBranchId(Integer branchId) {
            this.branchId = branchId;
            return this;
        }

        public Factory setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Factory setOrder(Order order) {
            this.order = order;
            return this;
        }

        /**
         * If inLowPrice is set to true, create() only return
         * commodities that in lowest price during three month
         *
         * @param inLowPrice whether get commodities only in lowest price of three month
         */
        public Factory setInLowPrice(boolean inLowPrice) {
            this.inLowPrice = inLowPrice;
            return this;
        }

        private String getHQL() {
            // build SQL query command
            String query = "FROM Commodity WHERE 1=1";

            if (IntegerUtils.isPositive(id)) {
                query += " AND id=" + id;
            }

            if (IntegerUtils.isPositive(categoryId)) {
                String categoryIds = new CategoryDao().getSubInstancesAndSelfIdsString(categoryId);
                query += " AND categoryId IN (" + categoryIds + ")";
            }

            if (crawlDate != null) {
                query += " AND crawlDate='" + crawlDate.toString() + "'";
            }

            if (IntegerUtils.isPositive(userId)) {
                query += " AND userId=" + userId;
            }

            if (IntegerUtils.isPositive(branchId)) {
                String branchIds = new BranchDao().getAllSubInstancesAndSelfIdsString(branchId);
                query += " AND branchId IN (" + branchIds + ")";
            }

            if (withParameters != null) {
                if (withParameters) {
                    query += " AND (parameters IS NOT NULL AND parameters<>'')";
                } else {
                    query += " AND (parameters IS NULL OR parameters='')";
                }
            }

            if (keyWords != null && keyWords.length > 0) {
                for (String keyWord : keyWords) {
                    if (StringUtils.isNotBlank(keyWord)) {
                        query += " AND name LIKE '%" + StringEscapeUtils.escapeSql(keyWord) + "%'";
                    }
                }
            }

            if (active != null) {
                query += " AND active=" + active;
            }

            if (inLowPrice) {
                query += " AND inLowPrice=true";
            }

            List<String> orderItems = new ArrayList<>();
            if (active == null) {
                orderItems.add("active ASC");
                orderItems.add("deactivateByProgram DESC");
            }
            if (this.order != null) {
                orderItems.add(this.order.toString());
            }
            orderItems.add("id DESC");
            if (orderItems.size() > 0) {
                query += " ORDER BY " + StringUtils.join(orderItems, ",");
            }
            return query;
        }

        public long getCount() {
            @SuppressWarnings("unchecked")
            List<Long> list = HibernateUtils.getList("SELECT COUNT(*) " + getHQL());
            return list.get(0);
        }

        @SuppressWarnings("unchecked")
        public List<Commodity> getInstances(int firstResult, int maxResults) {
            return HibernateUtils.getList(getHQL(), firstResult, maxResults);
        }

        public List<Commodity> getInstances() {
            @SuppressWarnings("unchecked")
            List<Commodity> commodities = HibernateUtils.getList(getHQL());
            return commodities;
        }
    }
}
