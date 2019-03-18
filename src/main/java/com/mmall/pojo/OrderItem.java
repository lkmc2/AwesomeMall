package com.mmall.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "订单子表对象", description = "这是订单子表对象")
public class OrderItem {
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(value = "用户id", name = "userId", example = "13", required = true)
    private Integer userId;

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
    private Date createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-04-15 21:42:40", required = true)
    private Date updateTime;

    public OrderItem(Integer id, Integer userId, Long orderNo, Integer productId, String productName, String productImage, BigDecimal currentUnitPrice, Integer quantity, BigDecimal totalPrice, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.currentUnitPrice = currentUnitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}