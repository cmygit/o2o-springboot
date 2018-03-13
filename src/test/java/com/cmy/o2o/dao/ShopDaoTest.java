package com.cmy.o2o.dao;

import com.cmy.o2o.entity.Area;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-01 09:43.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void insertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setParentId(1L);
        shop.setOwnerId(owner.getUserId());
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("123");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());

        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void queryByShopId() {
        Shop shop = shopDao.queryByShopId(24);
        System.out.println("areaId:" + shop.getArea().getAreaId());
        System.out.println("areaName:" + shop.getArea().getAreaName());
    }

    @Test
    public void queryShopCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwnerId(owner.getUserId());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的总数：" + count);
    }

    @Test
    public void queryShopList() {
        Shop shopCondition = new Shop();
        ShopCategory childShopCategory = new ShopCategory();
        childShopCategory.setParentId(12L);
        shopCondition.setShopCategory(childShopCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 999);
        System.out.println("店铺列表的大小：" + shopList.size());
        for (Shop shop : shopList) {
            System.out.println(shop.getShopName());
        }
    }
}