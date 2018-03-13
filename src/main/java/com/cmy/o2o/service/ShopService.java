package com.cmy.o2o.service;

import com.cmy.o2o.dto.ImageHolder;
import com.cmy.o2o.dto.ShopExecution;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.exception.ShopOperationException;

import java.io.InputStream;

/**
 * Author : cmy
 * Date   : 2018-03-01 15:35.
 * desc   :
 */
public interface ShopService {

    /**
     * 根据shopCondition分页返回数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     *
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     *
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param thumbnail
     *
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 注册店铺信息，包括对图片处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
