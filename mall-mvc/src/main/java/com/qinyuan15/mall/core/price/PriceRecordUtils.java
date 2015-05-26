package com.qinyuan15.mall.core.price;

import com.qinyuan15.mall.core.dao.PriceRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for PriceRecord
 * Created by qinyuan on 15-1-13.
 */
public class PriceRecordUtils {
    private PriceRecordUtils() {
    }

    /**
     * Group PriceRecord list by Commodity
     *
     * @param priceRecords priceRecord list to group
     * @return a map of Grouped PriceRecord, key is commodity and value is PriceRecords of that key
     */
    public static Map<Integer, List<PriceRecord>> groupByCommodityId(List<PriceRecord> priceRecords) {
        Map<Integer, List<PriceRecord>> map = new HashMap<>();
        for (PriceRecord priceRecord : priceRecords) {
            Integer commodityId = priceRecord.getCommodityId();
            if (!map.containsKey(commodityId)) {
                map.put(commodityId, new ArrayList<PriceRecord>());
            }
            map.get(commodityId).add(priceRecord);
        }
        return map;
    }

    public static Double getLowestPrice(List<PriceRecord> priceRecords) {
        Double lowestPrice = null;
        for (PriceRecord priceRecord : priceRecords) {
            Double price = priceRecord.getPrice();
            if (lowestPrice == null) {
                lowestPrice = price;
            } else {
                if (price < lowestPrice) {
                    lowestPrice = price;
                }
            }
        }
        return lowestPrice;
    }
}
