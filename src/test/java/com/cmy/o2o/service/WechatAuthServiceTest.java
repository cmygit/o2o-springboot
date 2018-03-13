package com.cmy.o2o.service;

import com.cmy.o2o.dto.WechatAuthExecution;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.WechatAuth;
import com.cmy.o2o.enums.WechatAuthStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-10 18:07.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest {

    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void getWechatAuthByOpenId() {
    }

    @Test
    public void register() {
        // 新增一条微信帐号
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "666";
        // 给微信帐号设置上用户信息，但不设置用户id
        // 预期创建微信帐号的时候同时创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("test");
        personInfo.setAdminFlag(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(), wae.getState());
        // 通过openId找到新增的wechatAuth
        wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
        // 打印用户名字
        System.out.println(wechatAuth.getPersonInfo().getName());
    }
}