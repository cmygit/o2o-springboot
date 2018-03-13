package com.cmy.o2o.web.shopadmin;

import com.cmy.o2o.entity.Product;
import com.cmy.o2o.enums.ProductCategoryStateEnum;
import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.ProductCategoryExecution;
import com.cmy.o2o.dto.Result;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-03-05 11:45.
 * desc   :
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/getproductcategoryList")
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
        List<ProductCategory> productCategoryList = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, productCategoryList);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }
    }

    @PostMapping(value = "/addproductcategorys")
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请至少输入一个商品类别");
        }
        return modelMap;
    }

    @PostMapping(value = "/removeproductcategory")
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId,
                                                      HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请至少选择一个商品类别");
        }
        return modelMap;
    }
}
