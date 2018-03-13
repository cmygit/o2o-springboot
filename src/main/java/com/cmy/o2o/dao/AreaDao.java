package com.cmy.o2o.dao;

import com.cmy.o2o.entity.Area;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-02-27 17:11.
 * desc   :
 */
public interface AreaDao {

    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
