package com.cmy.o2o.dto;

import com.cmy.o2o.enums.ProductCategoryStateEnum;
import com.cmy.o2o.entity.ProductCategory;
import lombok.Data;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-05 14:18.
 * desc   :
 */
@Data
public class ProductCategoryExecution {
    // 结果状态
    private int state;

    /* 状态标志 */
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    /**
     * 操作失败时使用的构造器
     * @param stateEnum
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 操作成功时使用的构造器
     * @param stateEnum
     * @param productCategoryList
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,
            List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }
}
