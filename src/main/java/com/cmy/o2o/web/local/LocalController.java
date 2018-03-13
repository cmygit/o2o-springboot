package com.cmy.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author : cmy
 * Date   : 2018-03-12 15:57.
 * desc   :
 */
@Controller
@RequestMapping("local")
public class LocalController {

    /**
     * 帐号绑定页面路由
     *
     * @return
     */
    @GetMapping(value = "/accountbind")
    private String accountbind() {
        return "local/accountbind";
    }

    /**
     * 修改密码页面路由
     *
     * @return
     */
    @GetMapping(value = "/changepsw")
    private String changepsw() {
        return "local/changepsw";
    }

    /**
     * 登录页面路由
     *
     * @return
     */
    @GetMapping(value = "/login")
    private String login() {
        return "local/login";
    }

}
