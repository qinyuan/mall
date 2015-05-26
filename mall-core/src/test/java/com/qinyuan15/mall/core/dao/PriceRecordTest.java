package com.qinyuan15.mall.core.dao;

import org.junit.Test;

/**
 * Test Persistent Object PriceRecord
 * Created by qinyuan on 15-1-4.
 */
public class PriceRecordTest {

    @Test
    public void testSaveDelete() throws Exception {
        /*
        Session session = HibernateUtil.getSession();

        PriceRecord record = new PriceRecord();
        record.setCommodityId(1);
        record.setRecordTime(new Date(System.currentTimeMillis()));
        record.setPrice(10.0);

        Integer id = (Integer) session.save(record);
        HibernateUtil.commit(session);

        session = HibernateUtil.getSession();
        record = (PriceRecord) session.get(PriceRecord.class, id);
        assertThat(record).isExactlyInstanceOf(PriceRecord.class);

        session.delete(record);
        HibernateUtil.commit(session);

        session = HibernateUtil.getSession();
        assertThat(session.get(PriceRecord.class, id)).isNull();
        */
    }
}
