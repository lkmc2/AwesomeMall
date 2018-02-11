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
}
