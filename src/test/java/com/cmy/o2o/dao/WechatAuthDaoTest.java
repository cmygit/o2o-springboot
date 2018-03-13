package com.cmy.o2o.dao;

import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.WechatAuth;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-10 17:10.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void tBqueryWechatInfoByOpenId() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(12L);
        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId("233233");
        wechatAuth.setCreateTime(new Date());
        int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void tAinsertWechatAuth() {
        WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("233233");
        assertEquals("i love you", wechatAuth.getPersonInfo().getName());
    }
}