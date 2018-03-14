package com.cmy.o2o.service.impl;

import com.cmy.o2o.dao.PersonInfoDao;
import com.cmy.o2o.dto.PersonInfoExecution;
import com.cmy.o2o.dto.WechatAuthExecution;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.enums.PersonInfoStateEnum;
import com.cmy.o2o.enums.WechatAuthStateEnum;
import com.cmy.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-03-10 18:49.
 * desc   :
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }

    @Override
    public PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    @Transactional
    public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
        // 空值判断
        if (personInfo == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
        }
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setCustomerFlag(1);
        personInfo.setEnableStatus(1);
        personInfo.setShopOwnerFlag(1);
        try {
            int effectedNum = personInfoDao.insertPersonInfo(personInfo);
            if (effectedNum <= 0) {
                return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertPersonInfo error:" + e.toString());
        }
        return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
    }

    @Override
    public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
        return null;
    }
}
