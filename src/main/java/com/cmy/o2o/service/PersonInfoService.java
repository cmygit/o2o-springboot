package com.cmy.o2o.service;

import com.cmy.o2o.dto.PersonInfoExecution;
import com.cmy.o2o.entity.PersonInfo;

/**
 * Author : cmy
 * Date   : 2018-03-10 18:47.
 * desc   :
 */
public interface PersonInfoService {

    /**
     *
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById(Long userId);

    /**
     *
     * @param personInfoCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition,
                                          int pageIndex, int pageSize);

    /**
     *
     * @param personInfo
     * @return
     */
    PersonInfoExecution addPersonInfo(PersonInfo personInfo);

    /**
     *
     * @param personInfo
     * @return
     */
    PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);
}
