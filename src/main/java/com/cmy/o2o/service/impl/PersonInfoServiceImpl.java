package com.cmy.o2o.service.impl;

import com.cmy.o2o.dao.PersonInfoDao;
import com.cmy.o2o.dto.PersonInfoExecution;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
        return null;
    }

    @Override
    public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
        return null;
    }
}
