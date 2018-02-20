package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by lkmc2 on 2018/2/2.
 * 常量类
 */
public class Const {
    public static final String CURRENT_USER = "currentUser"; //当前用户

    public static final String EMAIL = "email"; //邮箱
    public static final String USERNAME = "username"; //用户名

    public interface ProductListOrderBy { //产品列表排序（升序，降序）
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart { //购物车选中状态
        int CHECKED = 1; //已勾选
        int UN_CHECKED = 0; //未勾选

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL"; //限制产品数量失败
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS"; //限制产品数量成功
    }

    public interface Role { //用户角色
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1; // 管理员
    }

    //产品状态枚举
    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private int code; //产品编号
        private String value; //对应的产品值

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    //订单状态枚举
    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据传入的枚举码获取枚举
         * @param code 枚举码
         * @return 与枚举码一致的枚举
         */
        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) { //遍历所有枚举值
                if (orderStatusEnum.getCode() == code) { //传入的枚举存在
                    return orderStatusEnum; //返回当前枚举
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    //支付宝回调枚举
    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; //交易创建，等待买家付款
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS"; //交易支付成功

        String RESPONSE_SUCCESS = "success"; //成功响应
        String RESPONSE_FAILED = "failed"; //失败响应
    }

    //支付平台枚举
    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");

        PayPlatformEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    //支付类型枚举
    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        PaymentTypeEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据传入的枚举码获取枚举
         * @param code 枚举码
         * @return 与枚举码一致的枚举
         */
        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) { //遍历所有枚举值
                if (paymentTypeEnum.getCode() == code) { //传入的枚举存在
                    return paymentTypeEnum; //返回当前枚举
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


}
