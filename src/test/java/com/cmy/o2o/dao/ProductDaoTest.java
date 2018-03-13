package com.cmy.o2o.dao;

import com.cmy.o2o.entity.Product;
import com.cmy.o2o.entity.ProductCategory;
import com.cmy.o2o.entity.ProductImg;
import com.cmy.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-06 10:34.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAinsertProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        // 初始化3个商品实例并添加金shopId为1的店铺里
        // 同时商品类别Id也为1
        Product product1 = new Product();
        product1.setProductName("test1");
        product1.setProductDesc("desc1");
        product1.setImgAddr("addr1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);

        Product product2 = new Product();
        product2.setProductName("test2");
        product2.setProductDesc("desc2");
        product2.setImgAddr("addr2");
        product2.setPriority(2);
        product2.setEnableStatus(1);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);

        Product product3 = new Product();
        product3.setProductName("test3");
        product3.setProductDesc("desc3");
        product3.setImgAddr("addr3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);

        assertEquals(1, productDao.insertProduct(product1));
        assertEquals(1, productDao.insertProduct(product2));
        assertEquals(1, productDao.insertProduct(product3));
//        ProductImg productImg1 = new ProductImg();
//        productImg1.setImgAddr("pic1");
//        productImg1.setImgDesc("test1");
//        productImg1.setPriority(1);
//        productImg1.setCreateTime(new Date());
//        productImg1.setProductId(2L);
//        ProductImg productImg2 = new ProductImg();
//        productImg2.setImgAddr("pic2");
//        productImg2.setImgDesc("test2");
//        productImg2.setPriority(2);
//        productImg2.setCreateTime(new Date());
//        productImg2.setProductId(2L);
    }

    @Test
    public void testCqueryProductById() {
        long productId = 1;
        // 初始化2个商品详情图实例作为productId为1的商品下的详情图片
        // 批量插入到商品详情图表中
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("pic1");
        productImg1.setImgDesc("test1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("pic2");
        productImg2.setImgDesc("test2");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
        // 查询productId为1的商品信息并校验返回的详情图片实例列表的size是否为2
        Product product = productDao.queryProductById(productId);
        assertEquals(2, product.getProductImgList().size());
        // 删除新增的这2个商品详情图记录
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDupdateProduct() {
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("第一个产品");
        // 修改productId为1的商品的名称
        // 以及商品类别，并校验影响的行数是否为1
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void queryProductList() {
        Product productCondition = new Product();
        List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(3, productList.size());
        int count = productDao.queryProductCount(productCondition);
        assertEquals(12, count);
    }

    @Test
    public void queryProductCount() {
    }

    @Test
    public void updateProductCategoryToNull() {
        int effectedNum = productDao.updateProductCategoryToNull(4L);
        assertEquals(1, effectedNum);
    }

    @Test
    public void deleteProductById() {
    }
}