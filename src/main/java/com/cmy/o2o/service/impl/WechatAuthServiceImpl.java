package com.cmy.o2o.service.impl;

import com.cmy.o2o.dao.PersonInfoDao;
import com.cmy.o2o.dao.WechatAuthDao;
import com.cmy.o2o.dto.WechatAuthExecution;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.WechatAuth;
import com.cmy.o2o.enums.WechatAuthStateEnum;
import com.cmy.o2o.exception.WechatAuthOpeartionException;
import com.cmy.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-03-10 17:47.
 * desc   :
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    @Override
    @Transactional
    public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOpeartionException {
        // 空值判断
        if (wechatAuth == null || wechatAuth.getOpenId() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        // 设置创建时间
        wechatAuth.setCreateTime(new Date());
        // 如果微信帐号夹带这用户信息并且用户id为空，则认为该用户第一次登录平台（通过微信登录）
        // 则创建用户信息
        if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
            try {
                wechatAuth.getPersonInfo().setCreateTime(new Date());
                wechatAuth.getPersonInfo().setEnableStatus(1);
                PersonInfo personInfo = wechatAuth.getPersonInfo();
                int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                wechatAuth.setPersonInfo(personInfo);
                if (effectedNum <= 0) {
                    throw new WechatAuthOpeartionException("添加用户信息失败");
                }
            } catch (Exception e) {
                log.error("insertPersonInfo error:" + e.toString());
                throw new WechatAuthOpeartionException("insertPersonInfo error:" + e.getMessage());
            }
        }
        try {
            // 创建专属于本平台的微信帐号
            int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effectedNum <= 0) {
                throw new WechatAuthOpeartionException("帐号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            log.error("insertWechatAuth error:" + e.toString());
            throw new WechatAuthOpeartionException("insertWechatAuth error" + e.getMessage());
        }
    }
}
