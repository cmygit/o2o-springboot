package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 11:55.
 * desc   :
 */
@Data
public class Area {

    /* ID */
    private Integer areaId;

    /* 名称 */
    private String areaName;

    /* 权重 */
    private Integer priority;

    /* 创建时间 */
    private Date createTime;

    /* 更新时间 */
    private Date lastEditTime;
}
