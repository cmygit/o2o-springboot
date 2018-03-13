package com.cmy.o2o.dao;

import com.cmy.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-02 17:29.
 * desc   :
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryConditon") ShopCategory shopCategoryConditon);
}
