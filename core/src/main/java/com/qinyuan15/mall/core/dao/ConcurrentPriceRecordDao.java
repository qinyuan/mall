package com.qinyuan15.mall.core.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dao of PriceRecord, which is thread safe
 * Created by qinyuan on 15-4-19.
 */
public class ConcurrentPriceRecordDao {
    private final static Map<Integer, String> map = new ConcurrentHashMap<>();

    private boolean getLock(int commodityId) {
        String id = UUID.randomUUID().toString();
        if (map.containsKey(commodityId)) {
            return false;
        }

        map.put(commodityId, id);
        return map.get(commodityId).equals(id);
    }

    private void releaseLock(int commodityId) {
        map.remove(commodityId);
    }

    public void savePriceRecord(Date date, Double price, int commodityId) {
        if (!getLock(commodityId)) {
            return;
        }

        try {
            PriceRecord record = PriceRecordDao.factory().setCommodityId(commodityId)
                    .setRecordTime(date).getFirstInstance();
            if (record != null) {
                record.setPrice(price);
                record.setGrabTime(DateUtils.now());
                HibernateUtils.update(record);
            } else {
                record = new PriceRecord();
                record.setCommodityId(commodityId);
                record.setRecordTime(date);
                record.setPrice(price);
                record.setGrabTime(DateUtils.now());
                HibernateUtils.save(record);
            }
        } finally {
            releaseLock(commodityId);
        }
    }
}
