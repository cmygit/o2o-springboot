package com.cmy.o2o.service;

import com.cmy.o2o.dto.ImageHolder;
import com.cmy.o2o.dto.ProductExecution;
import com.cmy.o2o.entity.Product;
import com.cmy.o2o.exception.ProductOperationException;

import java.io.InputStream;
import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-06 11:01.
 * desc   :
 */
public interface ProductService {

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊查询），商品状态，店铺id，店铺类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     *
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 通过商品id查询唯一的商品信息
     * @param productId
     *
     * @return
     */
    Product getProductById(long productId);

    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     *
     * @return
     *
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException;

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     *
     * @return
     *
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException;
}
