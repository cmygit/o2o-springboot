package com.cmy.o2o.dao;

import com.cmy.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-05 11:37.
 * desc   :
 */
public interface ProductCategoryDao {

    /**
     * 通过shop id查询商店类别
     * @param shopId
     *
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     *
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     * @param productCategoryId
     *
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,
                              @Param("shopId") long shopId);
}
