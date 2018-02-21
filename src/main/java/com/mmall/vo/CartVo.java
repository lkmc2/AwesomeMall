package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车Value Object
 */

public class CartVo {

    private List<CartProductVo> cartProductVoList; //购物车产品值对象列表
    private BigDecimal cartTotalPrice; //购物车总价
    private Boolean allChecked; //是否全勾选
    private String imageHost; //图片主机前缀

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
