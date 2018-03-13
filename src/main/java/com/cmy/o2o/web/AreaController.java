package com.cmy.o2o.web;

import com.cmy.o2o.entity.Area;
import com.cmy.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-02-27 20:00.
 * desc   :
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {

    private Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @GetMapping(value = "/listarea")
    @ResponseBody
    private Map<String, Object> listArea() {
        logger.info("===start===");
        long startTIme = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelMap.put("rows", list);
            modelMap.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        logger.error("test error log");
        long endTiem = System.currentTimeMillis();
        logger.debug("costTIme:[{}ms]", endTiem - startTIme);
        logger.info("===end===");
        return modelMap;
    }
}
