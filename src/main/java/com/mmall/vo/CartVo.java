package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车Value Object
 */
@ApiModel(value = "购物车VO对象", description = "这是购物车VO对象")
public class CartVo {
    @ApiModelProperty(value = "购物车产品值对象列表", name = "cartProductVoList", required = true)
    private List<CartProductVo> cartProductVoList;

    @ApiModelProperty(value = "购物车总价", name = "cartTotalPrice", example = "1500", required = true)
    private BigDecimal cartTotalPrice;

    @ApiModelProperty(value = "是否全勾选", name = "allChecked", example = "1", required = true)
    private Boolean allChecked;

    @ApiModelProperty(value = "图片主机前缀", name = "imageHost", example = "test", required = true)
    private String imageHost;

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
