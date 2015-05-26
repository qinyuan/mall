package com.qinyuan15.mall.core.commodity;

import com.qinyuan15.mall.core.dao.Commodity;
import com.qinyuan15.mall.core.image.PictureUrlConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build CommoditySnapshot
 * Created by qinyuan on 15-2-27.
 */
public class CommoditySnapshotBuilder {
    public List<CommoditySnapshot> build(List<Commodity> commodities,
                                         PictureUrlConverter pictureUrlConverter) {
        List<CommoditySnapshot> snapshots = new ArrayList<>();
        for (Commodity commodity : commodities) {
            CommoditySnapshot snapshot = new CommoditySnapshot(commodity, pictureUrlConverter);
            snapshots.add(snapshot);
        }
        return snapshots;
    }
}
