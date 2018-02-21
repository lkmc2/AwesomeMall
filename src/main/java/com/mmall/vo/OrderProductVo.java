package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/21.
 * 订单产品值对象
 */

public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList; //订单子项值对象列表
    private BigDecimal productTotalPrice; //产品总价
    private String imageHost; //图片地址主机名

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
