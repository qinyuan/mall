package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.commodity.CommoditySnapshot;
import com.qinyuan15.mall.core.commodity.CommoditySnapshotBuilder;
import com.qinyuan15.mall.core.commodity.SnapshotConfig;
import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.core.dao.CommodityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to query commodity snapshot
 * Created by qinyuan on 15-2-27.
 */
@Controller
public class CommoditySnapshotController extends ImageController {

    @Autowired
    private SnapshotConfig snapshotConfig;

    @ResponseBody
    @RequestMapping("/json/commoditySnapshot.json")
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "active", required = false) Boolean active,
                        @RequestParam(value = "keyWord", required = false) String keyWord,
                        @RequestParam(value = "branchId", required = false) Integer branchId,
                        @RequestParam(value = "orderField", required = false) String orderField,
                        @RequestParam(value = "orderType", required = false) String orderType,
                        @RequestParam(value = "inLowPrice", required = false) Boolean inLowPrice,
                        @RequestParam(value = "pageNumber", required = true) Integer pageNumber) {
        // set default value of active to true
        if (active == null) {
            active = true;
        }
        if (inLowPrice == null) {
            inLowPrice = false;
        }
        CommodityDao.Factory factory = CommodityDao.factory().setCategoryId(categoryId)
                .setActive(active).setKeyWord(keyWord).setBranchId(branchId)
                .setInLowPrice(inLowPrice);

        if (StringUtils.hasText(orderField) && StringUtils.hasText(orderType)) {
            CommodityDao.Order order = new CommodityDao.Order()
                    .setField(CommodityDao.OrderField.create(orderField))
                    .setType(CommodityDao.OrderType.create(orderType));
            factory.setOrder(order);
        }

        if (!isPositive(pageNumber)) {
            pageNumber = 0;
        }

        int loadSize = snapshotConfig.getLoadSize();
        int firstResult = pageNumber * loadSize;
        List<Commodity> commodities = factory.getInstances(firstResult, loadSize + 1);

        Map<String, Object> result = new HashMap<>();
        if (commodities.size() == loadSize + 1) {
            result.put("hasNext", true);
            commodities = commodities.subList(0, loadSize);
        } else {
            result.put("hasNext", false);
        }

        List<CommoditySnapshot> snapshots = new CommoditySnapshotBuilder().build(
                commodities, pictureUrlConverter);
        result.put("snapshots", snapshots);
        return toJson(result);
    }
}
