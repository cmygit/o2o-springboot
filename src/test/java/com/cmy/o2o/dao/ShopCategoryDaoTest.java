package com.cmy.o2o.dao;

import com.cmy.o2o.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-02 17:37.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() {
        List<ShopCategory> shopCategoryList1 = shopCategoryDao.queryShopCategory(null);
        assertEquals(1, shopCategoryList1.size());
        List<ShopCategory> shopCategoryList2 = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(2, shopCategoryList2.size());
    }
}