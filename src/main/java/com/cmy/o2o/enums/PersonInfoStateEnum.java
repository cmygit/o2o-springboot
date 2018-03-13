package com.cmy.o2o.enums;

import lombok.Getter;

/**
 * Author : cmy
 * Date   : 2018-03-10 18:48.
 * desc   :
 */
@Getter
public enum PersonInfoStateEnum {

    SUCCESS(0, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "用户信息为空");

    private int state;

    private String stateInfo;

    private PersonInfoStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PersonInfoStateEnum stateOf(int index) {
        for (PersonInfoStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
