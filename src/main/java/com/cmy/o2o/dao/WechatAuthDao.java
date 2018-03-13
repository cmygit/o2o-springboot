package com.cmy.o2o.dao;

import com.cmy.o2o.entity.WechatAuth;

/**
 * Author : cmy
 * Date   : 2018-03-10 16:28.
 * desc   :
 */
public interface WechatAuthDao {

    /**
     * 通过openId查询对应平台的微信帐号
     * @param openId
     *
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     * 添加对应平台的微信帐号
     * @param wechatAuth
     *
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);
}
