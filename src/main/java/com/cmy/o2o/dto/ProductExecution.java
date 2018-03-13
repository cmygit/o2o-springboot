package com.cmy.o2o.dto;

import com.cmy.o2o.entity.Product;
import com.cmy.o2o.enums.ProductStateEnum;
import lombok.Data;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-06 10:54.
 * desc   :
 */
@Data
public class ProductExecution {

    /* 结果状态 */
    private int state;

    /* 状态标识 */
    private String stateInfo;

    /* 商品数量 */
    private int count;

    /* 操作的product（增删改商品时候使用） */
    private Product product;

    /* 获取的product列表（查询商品列表的时候使用） */
    private List<Product> productList;

    public ProductExecution() {
    }

    /**
     * 操作失败时使用的构造器
     * @param stateEnum
     */
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 单个商品操作成功时使用的构造器
     * @param stateEnum
     * @param product
     */
    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    /**
     * 多个商品操作成功时使用的构造器
     * @param stateEnum
     * @param productList
     */
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }
}
