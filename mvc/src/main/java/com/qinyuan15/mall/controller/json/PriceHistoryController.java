package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.PriceRecord;
import com.qinyuan15.mall.core.dao.PriceRecordDao;
import com.qinyuan15.mall.core.price.PriceRecordUtils;
import com.qinyuan15.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Query detail information of certain commodity
 * Created by qinyuan on 14-12-27.
 */
@Controller
public class PriceHistoryController extends BaseController {
    @ResponseBody
    @RequestMapping("/priceHistory.json")
    public String get(@RequestParam(value = "commodityId", required = false) Integer commodityId,
                      @RequestParam(value = "grabDate", required = false) String grabDate,
                      @RequestParam(value = "startTime", required = false) String startTime,
                      @RequestParam(value = "endTime", required = false) String endTime) {
        if (startTime == null) {
            startTime = DateUtils.threeMonthsAgo().toString();
        }
        if (endTime == null) {
            endTime = DateUtils.now().toString();
        }

        List<PriceRecord> priceRecords = PriceRecordDao.factory()
                .setCommodityId(commodityId)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setGrabDate(grabDate)
                .getInstances();

        Map<Integer, List<PriceRecord>> groupedRecords = PriceRecordUtils.groupByCommodityId(priceRecords);

        // adjust price records if price records has only one instance
        for (Map.Entry<Integer, List<PriceRecord>> entry : groupedRecords.entrySet()) {
            List<PriceRecord> records = entry.getValue();
            if (records.size() == 1 && !records.get(0).getRecordTime().toString().equals(startTime)) {
                PriceRecord record = PriceRecordDao.factory().setCommodityId(entry.getKey())
                        .setEndTime(startTime).setGrabDate(grabDate).getLastInstance();
                if (record != null) {
                    record.setRecordTime(DateUtils.newDate(startTime));
                } else {
                    record = new PriceRecord();
                    record.setRecordTime(DateUtils.newDate(startTime));
                    record.setPrice(records.get(0).getPrice());
                    record.setCommodityId(records.get(0).getCommodityId());
                    record.setGrabTime(records.get(0).getGrabTime());
                }
                records.add(record);
            }
        }

        return toJson(convert(groupedRecords));
    }

    /**
     * convert PriceRecord of a map to PriceRecordJson
     *
     * @param map map to convert
     * @return converted map
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, Map> convert(Map<Integer, List<PriceRecord>> map) {
        Map<Integer, Map> result = new TreeMap<>();
        for (Map.Entry<Integer, List<PriceRecord>> entry : map.entrySet()) {
            Integer commodityId = entry.getKey();
            if (!result.containsKey(commodityId)) {
                result.put(commodityId, new TreeMap());
                result.get(commodityId).put("prices", new ArrayList<PriceRecordJson>());
                result.get(commodityId).put("lowestPrice", PriceRecordUtils.getLowestPrice(entry.getValue()));
            }

            for (PriceRecord priceRecord : entry.getValue()) {
                PriceRecordJson priceRecordPair = new PriceRecordJson();
                priceRecordPair.date = toString(priceRecord.getRecordTime());
                priceRecordPair.price = priceRecord.getPrice();
                priceRecordPair.grabDate = toString(priceRecord.getGrabTime());
                ((List) result.get(commodityId).get("prices")).add(priceRecordPair);
            }
        }
        return result;
    }

    public String toString(Date date) {
        return date == null ? null : date.toString();
    }

    public static class PriceRecordJson {
        public String date;
        public Double price;
        public String grabDate;
    }
}
