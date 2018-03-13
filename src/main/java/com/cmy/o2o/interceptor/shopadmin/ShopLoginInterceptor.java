package com.cmy.o2o.interceptor.shopadmin;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Author : cmy
 * Date   : 2018-03-12 18:07.
 * desc   : 店家管理系统登录验证拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求前拦截
     *
     * @param request
     * @param response
     * @param handler
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从session中取出用户信息来
        Object userObj = request.getSession().getAttribute(ControllerConst.USER);
        if (userObj != null) {
            // 若用户信息补位空，则将session里的用户信息转换成PersonInfo实体类对象
            PersonInfo user = (PersonInfo) userObj;
            // 空值判断
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1) {
                // 通过验证，则返回true,后续流程继续进行
                return true;
            }
        }
        // 不满足验证，则直接跳转到帐号登录页面
        response.sendRedirect("/o2o/local/login?usertype=2");
        return false;
    }
}
