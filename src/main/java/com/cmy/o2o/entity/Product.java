package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:39.
 * desc   :
 */
@Data
public class Product {

    /**
     *
     */
    private Long productId;
    private String productName;
    private String productDesc;
    // 简略图
    private String imgAddr;
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    // 0.下架 1.在前端展示系统展示
    private Integer enableStatus;
    private Integer point;
    // 详情图片
    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;
}
