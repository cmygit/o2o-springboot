package com.cmy.o2o.dto;

import com.cmy.o2o.enums.ShopStateEnum;
import com.cmy.o2o.entity.Shop;
import lombok.Data;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-01 15:05.
 * desc   :
 */
@Data
public class ShopExecution {

    /* 结果状态 */
    private int state;

    /* 状态标识 */
    private String stateInfo;

    /* 店铺数量 */
    private int count;

    /* 操作的shop（增删改店铺的时候用到）*/
    private Shop shop;

    /* shop列表（查询店铺列表的时候使用）*/
    private List<Shop> shopList;

    public ShopExecution() {
    }


    /**
     * 店铺操作失败的时候使用的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 店铺操作成功的时候使用的构造器
     * @param stateEnum
     * @param shop
     */
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 店铺操作成功的时候使用的构造器（返回list）
     *
     * @param stateEnum
     * @param shopList
     */
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }
}
