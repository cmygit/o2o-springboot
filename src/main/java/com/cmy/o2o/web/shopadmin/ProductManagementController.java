package com.cmy.o2o.web.shopadmin;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.ImageHolder;
import com.cmy.o2o.dto.ProductExecution;
import com.cmy.o2o.entity.Product;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.entity.Shop;
import com.cmy.o2o.enums.ProductStateEnum;
import com.cmy.o2o.exception.ProductOperationException;
import com.cmy.o2o.service.ProductCategoryService;
import com.cmy.o2o.service.ProductService;
import com.cmy.o2o.util.CodeUtil;
import com.cmy.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-03-06 13:10.
 * desc   :
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /* 支持上传商品详情图的最大数量 */
    private final static int IMAGE_MAX_COUNT = 6;

    @PostMapping(value = "/addproduct")
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, ControllerConst.PRODUCT_STR);
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = getImageHolder((MultipartHttpServletRequest) request, thumbnail, productImgList);
            } else {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        try {
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        // 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                // 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
                product.setShop(currentShop);
                // 执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.toString());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder getImageHolder(MultipartHttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = request;
        // 取出缩略图并构建imageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
        }
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持6张图片的上传
        for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
                productImgList.add(productImg);
            } else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

    @GetMapping(value = "/getproductbyid")
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        // 有效值判断
        if (productId > -1) {
            // 获取商品信息
            Product product = productService.getProductById(productId);
            // 获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.
                    getProductCategoryList(product.getShop().getShopId());
            modelMap.put(ControllerConst.PRODUCT, product);
            modelMap.put(ControllerConst.PRODUCT_CATEGORY_LIST, productCategoryList);
            modelMap.put(ControllerConst.SUCCESS, true);
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty productId");
        }
        return modelMap;
    }

    @PostMapping(value = "/modifyproduct")
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 判断是商品编辑操作还是上下架操作
        // 商品编辑则进行验证码校验，上下架则跳过校验
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        // 校验验证码
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, ControllerConst.PRODUCT_STR);
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = getImageHolder((MultipartHttpServletRequest) request, thumbnail, productImgList);
            }
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        try {
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, e.toString());
            return modelMap;
        }
        // 空值判断
        if (product != null) {
            try {
                // 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
                product.setShop(currentShop);
                // 执行更新操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.toString());
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请输入商品信息");
        }
        return modelMap;
    }

    @GetMapping(value = "/getprodulistbyshop")
    @ResponseBody
    private Map<String, Object> getProduListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_INDEX);
        // 获取前台传过来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, ControllerConst.PAGE_SIZE);
        // 从当前session中获取店铺信息，主要是获取shopId
        Shop currentShop = (Shop) request.getSession().getAttribute(ControllerConst.CURRENT_SHOP);
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查询商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            long productCategoryId = HttpServletRequestUtil.getLong(request, ControllerConst.PRODUCT_CATEGORY_ID);
            String productName = HttpServletRequestUtil.getString(request, ControllerConst.PRODUCT_NAME);
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put(ControllerConst.PRODUCT_LIST, pe.getProductList());
            modelMap.put(ControllerConst.COUNT, pe.getCount());
            modelMap.put(ControllerConst.SUCCESS, true);
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private  Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 若有指定类别的要求则店家进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 若有商品模糊查询的要求则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}
