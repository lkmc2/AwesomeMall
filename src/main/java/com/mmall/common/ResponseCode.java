package com.mmall.common;

/**
 * Created by lkmc2 on 2018/2/2.
 * 响应码
 */
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"), //成功
    ERROR(1, "ERROR"), //错误
    NEED_LOGIN(10, "NEED_LOGIN"), //需要登陆
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"); //非法参数

    private final int code; //状态码
    private final String desc; //描述

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
