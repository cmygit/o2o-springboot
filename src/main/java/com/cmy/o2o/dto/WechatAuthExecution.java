package com.cmy.o2o.dto;

import com.cmy.o2o.entity.WechatAuth;
import com.cmy.o2o.enums.ProductCategoryStateEnum;
import com.cmy.o2o.enums.WechatAuthStateEnum;
import lombok.Data;

import java.util.List;

/**
 * Author : cmy
 * Date   : 2018-03-10 17:36.
 * desc   :
 */
@Data
public class WechatAuthExecution {
    // 结果状态
    private int state;

    /* 状态标志 */
    private String stateInfo;

    private int count;

    private WechatAuth wechatAuth;

    private List<WechatAuth> wechatAuthList;

    public WechatAuthExecution(WechatAuthStateEnum nullAuthInfo) {
    }

    /**
     * 操作失败时使用的构造器
     *
     * @param stateEnum
     */
    public WechatAuthExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 操作成功时使用的构造器
     *
     * @param stateEnum
     * @param wechatAuth
     */
    public WechatAuthExecution(ProductCategoryStateEnum stateEnum,
                               int count,
                               WechatAuth wechatAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.count = count;
        this.wechatAuth = wechatAuth;
    }

    /**
     * 操作成功时使用的构造器
     *
     * @param stateEnum
     * @param wechatAuthList
     */
    public WechatAuthExecution(ProductCategoryStateEnum stateEnum,
                               int count,
                               List<WechatAuth> wechatAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.count = count;
        this.wechatAuthList = wechatAuthList;
    }
}
