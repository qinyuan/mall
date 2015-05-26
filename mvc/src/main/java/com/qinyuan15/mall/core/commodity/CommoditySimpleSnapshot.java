package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.core.dao.CommodityPicture;
import com.qinyuan15.mall.core.dao.CommodityPictureDao;
import com.qinyuan15.mall.core.image.PictureUrlConverter;
import com.qinyuan15.utils.image.ThumbnailBuilder;

/**
 * Simple snapshot class of Commodity
 * Created by qinyuan on 15-2-27.
 */
public class CommoditySimpleSnapshot {
    private int id;
    private String serialNumber;
    private String name;
    private String picture;
    private Boolean active;
    private Boolean deactivateByProgram;
    private String url;

    public CommoditySimpleSnapshot(Commodity commodity, PictureUrlConverter pictureUrlConverter) {
        this.id = commodity.getId();
        this.serialNumber = commodity.getSerialNumber();
        this.name = commodity.getName();
        this.active = commodity.getActive();
        this.deactivateByProgram = commodity.getDeactivateByProgram();
        this.url = commodity.getUrl();
        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        CommodityPicture commodityPicture = new CommodityPictureDao().getFirstInstance(commodity.getId());
        if (commodityPicture != null) {
            String path = thumbnailBuilder.getMiddle(commodityPicture.getUrl());
            this.picture = pictureUrlConverter.pathToUrl(path);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getDeactivateByProgram() {
        return deactivateByProgram;
    }
}
