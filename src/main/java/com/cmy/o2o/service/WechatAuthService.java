package com.cmy.o2o.service;

import com.cmy.o2o.dto.WechatAuthExecution;
import com.cmy.o2o.entity.WechatAuth;
import com.cmy.o2o.exception.WechatAuthOpeartionException;

/**
 * Author : cmy
 * Date   : 2018-03-10 17:34.
 * desc   :
 */
public interface WechatAuthService {

    /**
     * 通过openId查找对应平台的微信帐号
     * @param openId
     *
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 注册本平台的微信帐号
     * @param wechatAuth
     *
     * @return
     *
     * @throws WechatAuthOpeartionException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOpeartionException;
}
