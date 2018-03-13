package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:35.
 * desc   :
 */
@Data
public class ProductCategory {

    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private String productCategoryDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
}
