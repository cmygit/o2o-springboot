package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:25.
 * desc   :
 */
@Data
public class ShopCategory {

    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Long parentId;

}
