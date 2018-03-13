package com.cmy.o2o.dto;

import lombok.Data;

/**
 * Author : cmy
 * Date   : 2018-03-05 11:48.
 * desc   : 封装json对象，所有返回结果都使用它
 */
@Data
public class Result<T> {

    // 是否成功标志
    private boolean success;

    // 成功时返回的数据
    private T data;

    // 错误信息
    private String errorMsg;

    // 错误码
    private int errorCode;

    public Result() {
    }

    /**
     * 成功时的构造器
     *
     * @param success
     * @param data
     */
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 错误时的构造器
     *
     * @param success
     * @param errorMsg
     * @param errorCode
     */
    public Result(boolean success, int errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
