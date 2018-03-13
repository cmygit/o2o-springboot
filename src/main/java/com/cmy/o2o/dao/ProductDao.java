package com.cmy.o2o.dao;

import com.cmy.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-06 09:37.
 * desc   :
 */
public interface ProductDao {

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     *
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询对应的商品总数
     * @param productCondition
     *
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 通过productId查询唯一的商品信息
     *
     * @param productId
     *
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 插入商品信息
     *
     * @param product
     *
     * @return
     */
    int insertProduct(Product product);

    /**
     * 更新商品信息
     *
     * @param product
     *
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前，将商品信息中的商品类别id置空
     * @param productCategoryId
     *
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

    /**
     * 根据商品id删除指定商品信息
     * @param productId
     *
     * @return
     */
    int deleteProductById(long productId);
}
