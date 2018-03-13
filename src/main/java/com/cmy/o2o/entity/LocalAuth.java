package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:38.
 * desc   :
 */
@Data
public class LocalAuth {

    private Long localAuthId;
    private String userName;
    private String password;
    private Long userId;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo personInfo;
}
