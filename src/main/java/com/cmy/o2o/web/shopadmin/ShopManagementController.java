package com.cmy.o2o.web.shopadmin;

import com.cmy.o2o.enums.ShopStateEnum;
import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.ImageHolder;
import com.cmy.o2o.dto.ShopExecution;
import com.cmy.o2o.entity.Area;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.entity.ShopCategory;
import com.cmy.o2o.service.AreaService;
import com.cmy.o2o.service.ShopCategoryService;
import com.cmy.o2o.service.ShopService;
import com.cmy.o2o.util.CodeUtil;
import com.cmy.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-03-01 20:18.
 * desc   :
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @GetMapping(value = "/getshopmanagementinfo")
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, ControllerConst.SHOP_ID);
        if (shopId <= 0) {
            // 从session中获取当前用户信息
            Object currentShopObj = request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
            if (currentShopObj == null) {
                // 获取的用户信息为空，表示未登陆，重定向
                modelMap.put(ControllerConst.REDIRECT, true);
                modelMap.put(ControllerConst.URL, "/o2o/shopadmin/shoplist");
            } else {
                // 获取到用户信息，作登录使用。
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put(ControllerConst.REDIRECT, false);
                modelMap.put(ControllerConst.SHOP_ID, currentShop.getShopId());
            }
        } else {
            // 将shopId保存到session中，做登录使用。
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute(ControllerConst.CURRENT_SHOP, currentShop);
            modelMap.put(ControllerConst.REDIRECT, false);
        }
        return modelMap;
    }

    @GetMapping(value = "/getshoplist")
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        // 仅测试时使用
//        user.setUserId(22L);
//        user.setName("test");
//        request.getSession().setAttribute(ControllerConst.USER, user);
        user = (PersonInfo) request.getSession().getAttribute(ControllerConst.USER);
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            // 列出店铺成功之后，将店铺信息放入session中作为权限验证依据，要求该帐号只能操作自己的店铺
            request.getSession().setAttribute(ControllerConst.SHOP_LIST, se.getShopList());
            modelMap.put(ControllerConst.SHOP_LIST, se.getShopList());
            modelMap.put(ControllerConst.USER, user);
            modelMap.put(ControllerConst.SUCCESS, true);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping(value = "/getshopbyid")
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put(ControllerConst.SUCCESS, true);
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.toString());
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty shopId");
        }
        return modelMap;
    }

    @GetMapping(value = "/getshopinitinfo")
    @ResponseBody
    private Map<String, Object> getShopInitInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put(ControllerConst.SUCCESS, true);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping(value = "/registershop")
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 1.接收并转换相应的参数，包括店铺信息以及图片信息。
        String shopStr = HttpServletRequestUtil.getString(request, ControllerConst.SHOP_STR);
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (resolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest =
                    (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "上传图片不能为空");
            return modelMap;
        }
        // 2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute(ControllerConst.USER);
            shop.setOwnerId(owner.getUserId());

            ShopExecution shopExecution = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
                shopExecution = shopService.addShop(shop, imageHolder);
                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                    // 从session中获取该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(ControllerConst.SHOP_LIST);
                    if (shopList == null || shopList.size() == 0) {
                        // 如果session没有数据，则新建数据
                        shopList = new ArrayList<>();
                    }
                    // 添加新增注册的店铺到数据中
                    shopList.add(shopExecution.getShop());
                    // 保存数据到session中
                    request.getSession().setAttribute(ControllerConst.SHOP_LIST, shopList);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.toString());
                return modelMap;
            }
            // 3.返回结果
            return modelMap;
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请输入店铺信息");
            return modelMap;
        }
    }

    @PostMapping(value = "/modifyshop")
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 1.接收并转换相应的参数，包括店铺信息以及图片信息。
        String shopStr = HttpServletRequestUtil.getString(request, ControllerConst.SHOP_STR);
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (resolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest =
                    (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        // 2.更相信店铺
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(shop, null);
                } else {
                    shopExecution = shopService.modifyShop(shop, imageHolder);
                }
                if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.toString());
                return modelMap;
            }
            // 3.返回结果
            return modelMap;
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请输入店铺id");
            return modelMap;
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用InputSteanmToFile产生异常" + e.toString());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("InputSteanmToFile关闭IO产生异常" + e.toString());
            }
        }
    }
}
