package com.qinyuan15.mall.core.dao;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test CommodityDao
 * Created by qinyuan on 15-1-14.
 */
public class CommodityDaoTest {
    @Test
    public void testFactory() {
        List<Commodity> commodities = CommodityDao.factory().getInstances();
        assertThat(commodities).isNotEmpty();
    }

    @Test
    public void testInLowPrice() {
        List<Commodity> commodities = CommodityDao.factory().setInLowPrice(true).getInstances();
        System.out.println(commodities.size());
    }

    @Test
    public void testSetUserId() {
        List<Commodity> commodities = CommodityDao.factory().setUserId(1).getInstances();
        System.out.println(commodities.size());
    }

    @Test
    public void testSetBranchId() {
        List<Commodity> commodities = CommodityDao.factory().setBranchId(2).getInstances();
        System.out.println(commodities.size());
        for (Commodity commodity : commodities) {
            assertThat(commodity.getBranchId()).isEqualTo(2);
        }
    }

    @Test
    public void testGetCount() {
        CommodityDao.Factory factory = CommodityDao.factory().setBranchId(2);
        assertThat(factory.getInstances()).hasSize((int) (factory.getCount()));
    }

    @Test
    public void testSetKeyWord() {
        List<Commodity> commodities = CommodityDao.factory().setKeyWord("兔耳朵").getInstances();
        System.out.println(commodities.size());
        for (Commodity commodity : commodities) {
            assertThat(commodity.getName()).contains("兔耳朵");
        }

        commodities = CommodityDao.factory().setKeyWord("o").getInstances();
        System.out.println(commodities.size());
        for (Commodity commodity : commodities) {
            assertThat(commodity.getName()).contains("o");
        }

        commodities = CommodityDao.factory().setKeyWord("o P").getInstances();
        System.out.println(commodities.size());
        for (Commodity commodity : commodities) {
            assertThat(commodity.getName()).contains("o").contains("P");
        }
    }

    @Test
    public void testSetCategoryId() {
        List<Commodity> commodities = CommodityDao.factory().setCategoryId(5).getInstances();
        System.out.println(commodities.size());
    }

    @Test
    public void testGetInstance() {
        CommodityDao dao = new CommodityDao();
        Commodity commodity = dao.getInstance(9);
        if (commodity != null) {
            System.out.println(commodity.getActive());
            System.out.println(commodity.getShowId());
            System.out.println(commodity.getSerialNumber());
        }
    }

    @Test
    public void testOrder() {
        CommodityDao.Order order = new CommodityDao.Order();

        assertThat(order.toString()).isEqualTo("discoverTime DESC");

        order.setField(CommodityDao.OrderField.PRICE);
        order.setType(CommodityDao.OrderType.ASC);

        assertThat(order.toString()).isEqualTo("price ASC");

        List<Commodity> commodities = CommodityDao.factory().setOrder(order).getInstances();
        Double previousPrice = null;
        for (Commodity commodity : commodities) {
            if (previousPrice != null && commodity.getPrice() != null) {
                assertThat(commodity.getPrice()).isGreaterThanOrEqualTo(previousPrice);
            }
            previousPrice = commodity.getPrice();
        }
    }

    @Test
    public void testGetInstancesByShowId() {
        List<Commodity> commodities = new CommodityDao().getInstancesByShowId("40780735321");
        System.out.println(commodities.size());
    }

    @Test
    public void testUpdateSales() {
        //new CommodityDao().updateSales(79, 1485);
    }

    @Test
    public void testUpdateDiscoverTime() {
        //new CommodityDao().updateDiscoverTime(79);
    }

    @Test
    public void testUpdateCrawlDate() {
        //new CommodityDao().updateCrawlDate(79);
    }

    @Test
    public void testUpdateInLowPrice() {
        //new CommodityDao().updateInLowPrice(41);
    }

    @Test
    public void testUpdateParameters() {
        //new CommodityDao().updateParameters(293, "");
    }

    @Test
    public void testDeactivate() {
        //new CommodityDao().deactivate(79);
    }

    @Test
    public void testActivate() {
        //new CommodityDao().activate(79);
    }

    @Test
    public void testGetWithParametersCount() {
        System.out.println(new CommodityDao().getWithParametersCount());
    }

    @Test
    public void testSetWithParameters() {
        System.out.println(CommodityDao.factory().getInstances().size());
        System.out.println(CommodityDao.factory().setWithParameters(true).getInstances().size());
        System.out.println(CommodityDao.factory().setWithParameters(false).getInstances().size());
    }

    @Test
    public void testHasDuplicateShowId() {
        System.out.println(new CommodityDao().hasDuplicateShowId(null, "37747062855"));
    }
}
