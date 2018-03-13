package com.cmy.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : cmy
 * Date   : 2018-03-02 14:34.
 * desc   : 主要用来解析路由并转发到相应的html中
 */
@Controller
@RequestMapping(value = "/shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation")
    private String shopOperation() {
        // 转发至店铺添加/编辑页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    private String shoplist() {
        // 转发至店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage")
    private String shopManagement() {
        // 转发至店铺管理页面
        return "shop/shopmanagement";
    }

    @GetMapping(value = "/productcategorymanage")
    private String productCategoryManage() {
        // 转发至商品类别管理页面
        return "shop/productcategorymanagement";
    }

    @GetMapping(value = "/productoperation")
    private String productOperation() {
        // 转发值商品添加/编辑页面
        return "shop/productoperation";
    }

    @GetMapping(value = "/productmanage")
    private String productmanage() {
        // 转发值商品管理页面
        return "shop/productmanage";
    }
}
