package com.cmy.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-02-27 12:20.
 * desc   :
 */
@Data
public class PersonInfo {

    private Long userId;
    private String name;
    private Date birthday;
    private String gender;
    private String phone;
    private String email;
    private String profileImg;
    private Integer customerFlag;
    private Integer shopOwnerFlag;
    private Integer adminFlag;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;

}
