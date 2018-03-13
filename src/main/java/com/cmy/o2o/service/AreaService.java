package com.cmy.o2o.service;

import com.cmy.o2o.entity.Area;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-02-27 17:50.
 * desc   :
 */

public interface AreaService {

    String AREALISTKEY = "arealist";

    /**
     * 获取区域列表
     *
     * @return
     */
    List<Area> getAreaList();
}
