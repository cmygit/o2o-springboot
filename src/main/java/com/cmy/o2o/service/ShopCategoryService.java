package com.cmy.o2o.service;

import com.cmy.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-02 21:09.
 * desc   :
 */
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
