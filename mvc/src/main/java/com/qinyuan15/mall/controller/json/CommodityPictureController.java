package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.CommodityPicture;
import com.qinyuan15.mall.core.dao.CommodityPictureDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query detail information of certain commodity
 * Created by qinyuan on 14-12-27.
 */
@Controller
public class CommodityPictureController extends ImageController {
    @ResponseBody
    @RequestMapping("/commodityPicture.json")
    public String index(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        CommodityPictureDao dao = new CommodityPictureDao();
        List<CommodityPicture> pictures = dao.getInstances(commodityId);
        List<CommodityPicture> detailPictures = dao.getDetailInstances(commodityId);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pictures", parseCommodityPictureSmallUrls(pictures));
        jsonMap.put("originalPictures", parseCommodityPictureUrls(pictures));
        jsonMap.put("detailPictures", parseCommodityPictureSmallUrls(detailPictures));
        jsonMap.put("originalDetailPictures", parseCommodityPictureUrls(detailPictures));
        return toJson(jsonMap);
    }
}
