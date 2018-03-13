package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 14:13.
 * desc   :
 */
@Data
public class WechatAuth {
    private Long wechatAuthId;
    private Long userId;
    private String openId;
    private Date createTime;
    private PersonInfo personInfo;
}
