package com.cmy.o2o.service;

import com.cmy.o2o.dto.ProductCategoryExecution;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.exception.ProductCategoryOperationException;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-05 11:42.
 * desc   :
 */
public interface ProductCategoryService {

    /**
     * 查询指定店铺下的所有商品类别信息
     * @param shopId
     *
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品类别信息
     * @param productCategoryList
     *
     * @return
     *
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;

    /**
     * 将次类别下的导航品里的类别id置为空，再删除该商品类别
     * @param productCategoryId
     * @param shopId
     *
     * @return
     *
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException;
}
