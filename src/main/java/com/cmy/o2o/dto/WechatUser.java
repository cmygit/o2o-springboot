package com.cmy.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author : cmy
 * Date   : 2018-03-10 13:22.
 * desc   : 微信用户实体类
 */
@Data
public class WechatUser implements Serializable {

    // openId，标识该公众号下面的该用户的唯一Id
    @JsonProperty("openid")
    private String openId;

    // 用户昵称
    @JsonProperty("nickname")
    private String nickName;

    // 性别
    @JsonProperty("sex")
    private int sex;

    // 省份
    @JsonProperty("province")
    private String province;

    //城市
    @JsonProperty("city")
    private String city;

    // 去
    @JsonProperty("country")
    private String country;

    // 头像图片地址
    @JsonProperty("headimgurl")
    private String headImgUrl;

    // 语言
    @JsonProperty("language")
    private String language;

    // 用户权限
    @JsonProperty("privilege")
    private String[] privilege;

}
