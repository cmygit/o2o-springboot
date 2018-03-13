package com.cmy.o2o.dao;

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
 * Date   : 2018-03-10 17:12.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonInfoDaoTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void tBqueryPersonInfoById() {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(12L);
        assertEquals("i love you", personInfo.getName());
    }

    @Test
    public void tAinsertPersonInfo() {
        // 设置新增用户的信息
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("i love you");
        personInfo.setGender("w");
        personInfo.setAdminFlag(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum = personInfoDao.insertPersonInfo(personInfo);
        assertEquals(1, effectedNum);
    }
}