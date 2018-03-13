package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:22.
 * desc   :
 */
@Data
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
