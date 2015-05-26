package com.qinyuan15.mall.core.price;

import com.qinyuan15.mall.core.dao.PriceRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test PriceRecordUtils
 * Created by qinyuan on 15-1-13.
 */
public class PriceRecordUtilsTest {
    @Test
    public void testGroupByCommodityId() throws Exception {
        List<PriceRecord> priceRecords = new ArrayList<PriceRecord>();
        priceRecords.add(mockPriceRecord(1, 1));
        priceRecords.add(mockPriceRecord(2, 2));
        priceRecords.add(mockPriceRecord(3, 3));
        priceRecords.add(mockPriceRecord(4, 3));
        Map<Integer, List<PriceRecord>> map = PriceRecordUtils.groupByCommodityId(priceRecords);

        assertThat(map).hasSize(3);
        assertThat(map.get(1)).hasSize(1);
        assertThat(map.get(1).get(0).getId()).isEqualTo(1);
        assertThat(map.get(2)).hasSize(1);
        assertThat(map.get(2).get(0).getId()).isEqualTo(2);
        assertThat(map.get(3)).hasSize(2);
        assertThat(map.get(3).get(0).getId()).isEqualTo(3);
        assertThat(map.get(3).get(1).getId()).isEqualTo(4);
    }

    private PriceRecord mockPriceRecord(int id, int commodityId) {
        PriceRecord priceRecord = new PriceRecord();
        priceRecord.setId(id);
        priceRecord.setCommodityId(commodityId);
        return priceRecord;
    }
}
