package com.cmy.o2o.web.frontend;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.ProductExecution;
import com.cmy.o2o.entity.Product;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.service.ProductCategoryService;
import com.cmy.o2o.service.ProductService;
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
 * Date   : 2018-03-08 17:37.
 * desc   :
 */
@RestController
@RequestMapping(value = "frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     *
     * @param request
     *
     * @return
     */
    @GetMapping(value = "listshopdetailpageinfo")
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取前台传过来的shopid
        long shopId = HttpServletRequestUtil.getLong(request, ControllerConst.SHOP_ID);
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            try {
                // 获取店铺id为shopid的店铺信息
                shop = shopService.getByShopId(shopId);
                // 获取店铺下面的商品类别列表
                productCategoryList = productCategoryService.getProductCategoryList(shopId);
                modelMap.put(ControllerConst.SHOP, shop);
                modelMap.put(ControllerConst.PRODUCT_CATEGORY_LIST, productCategoryList);
                modelMap.put(ControllerConst.SUCCESS, true);
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty shopId");
        }
        return modelMap;
    }

    @GetMapping(value = "listproductsbyshop")
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_INDEX);
        // 获取一页需要显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_SIZE);
        // 获取店铺id
        long shopId = HttpServletRequestUtil.getLong(request, ControllerConst.SHOP_ID);
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            // 获取商品类别id
            long productCategoryId = HttpServletRequestUtil.getLong(request, ControllerConst.PRODUCT_CATEGORY_ID);
            // 尝试获取模糊查找的商品名
            String productName = HttpServletRequestUtil.getString(request, ControllerConst.PRODUCT_NAME);
            // 组合查询条件
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            try {
                // 按照传入的查询条件及分页信息返回相应商品列表以及总数
                ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
                modelMap.put(ControllerConst.PRODUCT_LIST, pe.getProductList());
                modelMap.put(ControllerConst.COUNT, pe.getCount());
                modelMap.put(ControllerConst.SUCCESS, true);
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty pageIndex or pageSize or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1) {
            // 查询某个商品类别下面的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            // 查询名字里包含productName的店铺列表
            productCondition.setProductName(productName);
        }
        // 值允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
