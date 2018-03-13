package com.cmy.o2o.web.frontend;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.entity.HeadLine;
import com.cmy.o2o.entity.ShopCategory;
import com.cmy.o2o.service.HeadLineService;
import com.cmy.o2o.service.ShopCategoryService;
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
 * Date   : 2018-03-07 19:21.
 * desc   :
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    @GetMapping(value = "/listmainpageinfo")
    @ResponseBody
    private Map<String, Object> listMainPageInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        try {
            // 获取一级店铺类别列表（即parentId为空的ShopCategory）
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put(ControllerConst.SHOP_CATEGORY_LIST, shopCategoryList);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try {
            HeadLine headLinecCondition = new HeadLine();
            headLinecCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLinecCondition);
            modelMap.put(ControllerConst.HEAD_LINE_LIST, headLineList);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
        }
        modelMap.put(ControllerConst.SUCCESS, true);
        return modelMap;
    }
}
