package com.cmy.o2o.web.frontend;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.ShopExecution;
import com.cmy.o2o.entity.Area;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.entity.ShopCategory;
import com.cmy.o2o.service.AreaService;
import com.cmy.o2o.service.ShopCategoryService;
import com.cmy.o2o.service.ShopService;
import com.cmy.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-03-08 13:34.
 * desc   :
 */
@RestController
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    /**
     * 返回商店列表页里的ShopCategory列表（二级或者一级），以及区域信息列表
     *
     * @param request
     *
     * @return
     */
    @GetMapping(value = "/listshopspageinfo")
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 从请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, ControllerConst.PARENT_ID);

        ShopCategory shopCategoryCondition = new ShopCategory();
        if (parentId != -1) {
            // 如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
            shopCategoryCondition.setParentId(parentId);
        } else {
            // 如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商品列表的时候）
            shopCategoryCondition = null;
        }

        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            modelMap.put(ControllerConst.SHOP_CATEGORY_LIST, shopCategoryList);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
            return modelMap;
        }

        List<Area> areaList = null;
        try {
            // 获取区域 表信息
            areaList = areaService.getAreaList();
            modelMap.put(ControllerConst.AREA_LIST, areaList);
            modelMap.put(ControllerConst.SUCCESS, true);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping(value = "/listshops")
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_INDEX);
        // 获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_SIZE);
        // 非空判断
        if ((pageIndex > -1) && (pageSize > -1)) {
            // 获取一级类别
            long parentId = HttpServletRequestUtil.getLong(request, ControllerConst.PARENT_ID);
            // 获取二级类别
            long shopCategoryId = HttpServletRequestUtil.getLong(request, ControllerConst.SHOP_CATEGORY_ID);
            // 获取区域id
            int areaId = HttpServletRequestUtil.getInt(request, ControllerConst.AREA_ID);
            // 获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, ControllerConst.SHOP_NAME);
            // 组合查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            try {
                // 根据查询条件和分页信息获取店铺列表并返回
                ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
                modelMap.put(ControllerConst.SHOP_LIST, se.getShopList());
                modelMap.put(ControllerConst.COUNT, se.getCount());
                modelMap.put(ControllerConst.SUCCESS, true);
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            // 查询某个以及ShopCategory下面的所有二级ShopCategory里面的商铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setParentId(parentId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (shopCategoryId != -1L) {
            // 查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            // 查询位于某个区域id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        return shopCondition;
    }
}
