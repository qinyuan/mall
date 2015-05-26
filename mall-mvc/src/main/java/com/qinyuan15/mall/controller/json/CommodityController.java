package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.*;
import com.qinyuan15.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Query detail information of certain commodity
 * Created by qinyuan on 14-12-27.
 */
@Controller
public class CommodityController extends ImageController {
    @ResponseBody
    @RequestMapping("/commodity.json")
    public String get(@RequestParam(value = "id", required = false) Integer id,
                      @RequestParam(value = "inLowPrice", required = false) String inLowPriceString) {
        boolean inLowPrice = false;

        if (inLowPriceString != null && !inLowPriceString.toLowerCase().equals("false")) {
            inLowPrice = true;
        }

        List<Commodity> commodities = CommodityDao.factory().setInLowPrice(inLowPrice)
                .setId(id).getInstances();

        return toJson(convert(commodities));
    }

    /**
     * Convert Commodity to CommodityJson
     *
     * @param commodities Commodity list to convert
     * @return A map containing CommodityJson
     */
    private Map<Integer, CommodityJson> convert(List<Commodity> commodities) {
        Map<Integer, CommodityJson> commodityJsonMap = new TreeMap<Integer, CommodityJson>();
        for (Commodity commodity : commodities) {
            Integer id = commodity.getId();
            CommodityJson commodityJson = new CommodityJson();
            commodityJson.name = commodity.getName();
            commodityJson.url = commodity.getUrl();
            commodityJson.discoverTime = commodity.getDiscoverTime();
            commodityJson.onShelf = isOnShelf(commodity);

            List<CommodityPicture> commodityPictures = new CommodityPictureDao().getInstances(id);
            commodityJson.pictures = parseCommodityPictureUrls(commodityPictures);
            commodityJsonMap.put(id, commodityJson);
        }
        return commodityJsonMap;
    }

    /**
     * check whether a commodity is on shelf
     *
     * @return true if on shelf
     */
    // TODO This way to check whether commodity on shelf is religious
    private boolean isOnShelf(Commodity commodity) {
        PriceRecord priceRecord = new PriceRecordDao().getLastInstance(commodity.getId());
        return DateUtils.getDayDiff(priceRecord.getRecordTime(), DateUtils.now()) <= 2;
    }

    public static class CommodityJson {
        public String name;
        public String url;
        public List<String> pictures;
        public String discoverTime;
        public boolean onShelf;
    }
}
