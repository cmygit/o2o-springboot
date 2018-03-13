package com.cmy.o2o.dao;

import com.cmy.o2o.entity.LocalAuth;
import com.cmy.o2o.entity.PersonInfo;
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
 * Date   : 2018-03-12 11:53.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username = "testusername";

    private static final String password = "testpassword";

    @Test
    public void tDqueryLocalByUserNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, "123456");
        assertEquals(username, localAuth.getUserName());
    }

    @Test
    public void tBqueryLocalByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(22L);
        assertEquals(username, localAuth.getUserName());
    }

    @Test
    public void tAinsertLocalAuth() {
        // 新增一条平台帐号信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(22L);
        // 给平台帐号绑定上用户信息
        localAuth.setPersonInfo(personInfo);
        // 设置上用户名和密码
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void tCupdateLocalAuth() {
        Date now = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(22L, username, password, "123456", now);
        assertEquals(1, effectedNum);
    }
}