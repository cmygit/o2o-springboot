package com.cmy.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author : cmy
 * Date   : 2018-03-07 20:28.
 * desc   :
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @GetMapping(value = "/index")
    private String inddex() {
        return "frontend/index";
    }

    @GetMapping(value = "/shopdetail")
    private String showShopDetail() {
        return "frontend/shopdetail";
    }

    @GetMapping(value = "/productdetail")
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    @GetMapping(value = "/shoplist")
    private String showShopList() {
        return "frontend/shoplist";
    }

}
