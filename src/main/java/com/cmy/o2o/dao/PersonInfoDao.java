package com.cmy.o2o.dao;

import com.cmy.o2o.entity.PersonInfo;

/**
 * Author : cmy
 * Date   : 2018-03-10 16:05.
 * desc   :
 */
public interface PersonInfoDao {

    /**
     * 通过用户id查询用户
     * @param userId
     *
     * @return
     */
    PersonInfo queryPersonInfoById(long userId);

    /**
     * 添加用户信息
     * @param personInfo
     *
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);
}
