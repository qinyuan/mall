package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.config.share.ShareLinks;
import com.qinyuan15.mall.core.dao.*;
import com.qinyuan15.utils.image.ThumbnailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Detail page
 * Created by qinyuan on 15-2-17.
 */
@Controller
public class DetailController extends ImageController {
    @Autowired
    private ShareLinks shareLinks;

    @RequestMapping("/detail")
    public String index(ModelMap model,
                        @RequestParam(value = "id", required = false) Integer id,
                        @RequestParam(value = "serial", required = false) String serial,
                        @RequestParam(value = "showId", required = false) String showId) {
        CommodityDao commodityDao = new CommodityDao();

        if (StringUtils.hasText(serial)) {
            id = commodityDao.getIdBySerialNumber(serial);
        } else if (StringUtils.hasText(showId)) {
            id = commodityDao.getIdByShowId(showId);
        }

        if (!isPositive(id)) {
            return BLANK;
        }

        // commodity
        Commodity commodity = commodityDao.getInstance(id);
        if (commodity == null) {
            return BLANK;
        }

        model.addAttribute("commodity", commodity);

        // commodity picture
        CommodityPictureDao pictureDao = new CommodityPictureDao();
        List<CommodityPicture> pictures = pictureDao.getInstances(id);
        model.addAttribute("pictures", parseCommodityPictureUrls(pictures));
        model.addAttribute("middlePictures", parseCommodityPictureMiddleUrls(pictures));
        model.addAttribute("smallPictures", parseCommodityPictureSmallUrls(pictures));
        List<CommodityPicture> detailPictures = pictureDao.getDetailInstances(id);
        model.addAttribute("detailPictures", parseCommodityPictureUrls(detailPictures));

        // related commodity
        Integer relatedCommoditySize = getAppConfig().getRelatedCommoditySize();
        List<Commodity> commodities = new RelatedCommodityDao()
                .setLimitSize(relatedCommoditySize).getInstances(commodity);
        model.addAttribute("relatedCommodities", createRelatedCommodityWrappers(commodities));

        // branch
        Branch branch = commodity.getBranch();
        model.addAttribute("branch", adjustBranch(branch));

        // price
        CommodityPriceDao priceDao = new CommodityPriceDao();
        model.addAttribute("currentPrice", priceDao.getCurrentPrice(id));
        model.addAttribute("highPrice", priceDao.getMaxPrice(id));

        // appraise
        AppraiseGroupDao appraiseGroupDao = new AppraiseGroupDao();
        model.addAttribute("positiveAppraiseGroups", appraiseGroupDao.getPositiveInstances(id));
        model.addAttribute("negativeAppraiseGroups", appraiseGroupDao.getNegativeInstances(id));

        // share
        model.put("share", shareLinks);

        // JavaScript files
        addJs("commodity-parameters");
        addJs("lib/linecharts/raphael-min");
        addJs("lib/linecharts/linecharts");
        addHeadJs("image-adjust");
        addIEJs("detail-ie-patch");

        setTitle("商品明细-" + commodity.getName());

        return "detail";
    }

    private List<RelatedCommodityWrapper> createRelatedCommodityWrappers(
            List<Commodity> commodities) {
        List<RelatedCommodityWrapper> wrappers = new ArrayList<>();
        CommodityPictureDao pictureDao = new CommodityPictureDao();

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        for (Commodity commodity : commodities) {
            CommodityPicture picture = pictureDao.getFirstInstance(commodity.getId());
            RelatedCommodityWrapper wrapper = new RelatedCommodityWrapper();
            wrapper.id = commodity.getId();
            wrapper.picture = this.pictureUrlConverter.pathToUrl(
                    thumbnailBuilder.getMiddle(picture.getUrl()));
            wrapper.price = commodity.getPrice();
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    public static class RelatedCommodityWrapper {
        private Integer id;
        private String picture;
        private Double price;

        public Integer getId() {
            return id;
        }

        public String getPicture() {
            return picture;
        }

        public Double getPrice() {
            return price;
        }
    }
}
