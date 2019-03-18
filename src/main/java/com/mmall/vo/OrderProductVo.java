package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/21.
 * 订单产品值对象
 */
@ApiModel(value = "订单产品VO对象", description = "这是订单产品VO对象")
public class OrderProductVo {

    @ApiModelProperty(value = "订单子项值对象列表", name = "orderItemVoList", required = true)
    private List<OrderItemVo> orderItemVoList;

    @ApiModelProperty(value = "产品总价", name = "productTotalPrice", example = "1500", required = true)
    private BigDecimal productTotalPrice;

    @ApiModelProperty(value = "图片地址主机名", name = "imageHost", example = "test", required = true)
    private String imageHost;

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
