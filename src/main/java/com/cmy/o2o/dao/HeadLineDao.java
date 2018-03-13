package com.cmy.o2o.dao;

import com.cmy.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-07 19:00.
 * desc   :
 */
public interface HeadLineDao {

    /**
     * 根据传入的查询条件（头条名）查询头条
     * @param headLineCondition
     *
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
