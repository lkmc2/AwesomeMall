package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by lkmc2 on 2018/2/2.
 * 服务响应对象
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //属性为null时，该属性不显示
public class ServerResponse<T> implements Serializable {
    private int status; //返回状态
    private String msg; //消息
    private T data; //数据

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 获取数据状态是否成功
     * @return 是否成功
     */
    @JsonIgnore //该方法不会显示在json数据中
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 获取响应状态
     * @return 响应状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 获取响应消息
     * @return 响应消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 获取响应数据
     * @return 响应数据
     */
    public T getData() {
        return data;
    }

    /**
     * 生成一个成功类型的响应
     * @param <T> 响应的数据类型
     * @return 一个成功类型的响应
     */
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 生成一个成功类型，带返回消息的响应
     * @param <T> 响应的数据类型
     * @param msg 响应消息
     * @return 一个成功类型的响应
     */
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    /**
     * 生成一个成功类型，带返回数据的响应
     * @param <T> 响应的数据类型
     * @param data 响应数据
     * @return 一个成功类型的响应
     */
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    /**
     * 生成一个成功类型，带返回消息、返回数据的响应
     * @param <T> 响应的数据类型
     * @param msg 响应消息
     * @param data 响应数据
     * @return 一个成功类型的响应
     */
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 生成一个错误类型，带描述的响应
     * @param <T> 响应的数据类型
     * @return 一个错误类型的响应
     */
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    /**
     * 生成一个错误类型，带自定义描述的响应
     * @param <T> 响应的数据类型
     * @param errorMsg 错误信息
     * @return 一个错误类型的响应
     */
    public static <T> ServerResponse<T> createByErrorMessage(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    /**
     * 生成一个错误类型，带自定义状态码、自定义描述的响应
     * @param errorCode 错误状态码
     * @param errorMsg 错误信息
     * @param <T> 响应的数据类型
     * @return 一个错误类型的响应
     */
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMsg) {
        return new ServerResponse<T>(errorCode, errorMsg);
    }
}
