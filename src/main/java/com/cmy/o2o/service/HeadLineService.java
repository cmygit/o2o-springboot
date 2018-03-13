package com.cmy.o2o.service;

import com.cmy.o2o.entity.HeadLine;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-07 19:16.
 * desc   :
 */
public interface HeadLineService {

    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     *
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
