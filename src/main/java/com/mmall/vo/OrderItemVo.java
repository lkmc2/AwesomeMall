package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by lkmc2 on 2018/2/21.
 * 订单子项值对象
 */
@ApiModel(value = "订单子表VO对象", description = "这是订单子表VO对象")
public class OrderItemVo {
    @ApiModelProperty(value = "订单号", name = "orderNo", example = "1491753014256", required = true)
    private Long orderNo;

    @ApiModelProperty(value = "产品Id", name = "id", example = "1", required = true)
    private Integer productId;

    @ApiModelProperty(value = "产品名称", name = "name", example = "三星Fold", required = true)
    private String productName;

    @ApiModelProperty(value = "产品图片", name = "productImage", example = "F:/picture/phone.jpg")
    private String productImage;

    @ApiModelProperty(value = "生成订单时的商品单价", name = "currentUnitPrice", example = "17000", required = true)
    private BigDecimal currentUnitPrice;

    @ApiModelProperty(value = "数量", name = "quantity", example = "2", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "总价", name = "totalPrice", example = "34000", required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-04-13 21:42:40", required = true)
    private String createTime;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
