package com.cmy.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author : cmy
 * Date   : 2018-03-10 13:28.
 * desc   : 用户授权token
 */
@Data
public class UserAccessToken {

    // 获取到的凭证
    @JsonProperty("access_token")
    private String accessToken;

    // 凭证有效时间，单位：秒
    @JsonProperty("expires_in")
    private String expiresIn;

    // 表示更新令牌，用来获取下一次的访问令牌
    @JsonProperty("refresh_token")
    private String refreshToken;

    // 该用户再次公众号下的身份标志，对于此微信号具有唯一性
    @JsonProperty("openid")
    private String openId;

    // 表示权限范围
    @JsonProperty("scope")
    private String scope;
}
