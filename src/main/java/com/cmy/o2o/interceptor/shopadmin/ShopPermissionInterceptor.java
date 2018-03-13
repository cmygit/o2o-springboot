package com.cmy.o2o.interceptor.shopadmin;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-12 18:19.
 * desc   : 店铺操作权限校验的拦截器
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

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
        // 从session中取出店铺信息来
        Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
        // 从session中取出当前用户可操作的店铺列表
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(ControllerConst.SHOP_LIST);
        // 非空判断
        if (currentShop != null && shopList != null) {
            // 遍历可操作的店铺列表
            for (Shop shop : shopList) {
                // 如果当前店铺在可操作的列表里返回true，进行接下来的用户操作
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        // 若不满足拦截器的验证则返回false，终止用户操作的执行
        return false;
    }
}
