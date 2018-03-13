package com.cmy.o2o;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : cmy
 * Date   : 2018-03-12 23:11.
 * desc   :
 */
@Controller
public class Hello {

    @GetMapping(value = "hello")
    @ResponseBody
    public String hello() {
        return "hello spring boot";
    }

    @GetMapping(value = "hi")
    public String hi() {
        return "shop/shoplist";
    }
}
