package com.cmy.o2o.service.impl;

import com.cmy.o2o.dao.ProductDao;
import com.cmy.o2o.enums.ProductCategoryStateEnum;
import com.cmy.o2o.dao.ProductCategoryDao;
import com.cmy.o2o.dto.ProductCategoryExecution;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.exception.ProductCategoryOperationException;
import com.cmy.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-05 11:44.
 * desc   :
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("商品类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    // 包含2个写操作，需要启用事务
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        // 1.删除商品类别之前，将此商品类别下的商品的类别id置为空,解除tb_product里商品与该productCategoryId的关联
        try {
            int efftedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (efftedNum < 0) {
                throw new RuntimeException("商品类别删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("delete productCategory error:" + e.toString());
        }
        // 2.删除商品类别
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.toString());
        }
    }
}
