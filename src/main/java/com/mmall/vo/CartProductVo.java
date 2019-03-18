package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车产品值对象Value Object（结合了产品和购物车的一个抽象对象）
 */
@ApiModel(value = "购物车产品VO对象", description = "这是购物车产品VO对象")
public class CartProductVo {
    @ApiModelProperty(value = "id", name = "id", example = "1", required = true)
    private Integer id;

    @ApiModelProperty(value = "用户id", name = "userId", example = "13", required = true)
    private Integer userId;

    @ApiModelProperty(value = "产品id", name = "productId", example = "26", required = true)
    private Integer productId;

    @ApiModelProperty(value = "数量", name = "quantity", example = "2", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "产品名称", name = "productName", example = "三星Fold", required = true)
    private String productName;

    @ApiModelProperty(value = "产品子标题", name = "productSubtitle", example = "F:/picture/phone2.jpg")
    private String productSubtitle;

    @ApiModelProperty(value = "产品主图", name = "productMainImage", example = "F:/picture/phone.jpg")
    private String productMainImage;

    @ApiModelProperty(value = "价格", name = "productPrice", example = "17000", required = true)
    private BigDecimal productPrice;

    @ApiModelProperty(value = "状态", name = "productStatus", example = "1", required = true)
    private Integer productStatus;

    @ApiModelProperty(value = "产品总价", name = "productTotalPrice", example = "34000", required = true)
    private BigDecimal productTotalPrice;

    @ApiModelProperty(value = "产品库存", name = "productStock", example = "200", required = true)
    private Integer productStock;

    @ApiModelProperty(value = "是否选择,1=已勾选,0=未勾选", name = "productChecked", example = "1", required = true)
    private Integer productChecked;

    @ApiModelProperty(value = "限制数量的一个返回结果", name = "limitQuantity", example = "1", required = true)
    private String limitQuantity;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
