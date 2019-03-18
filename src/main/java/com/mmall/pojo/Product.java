package com.mmall.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "产品对象", description = "这是产品对象")
public class Product {
    @ApiModelProperty(value = "产品Id", name = "id", example = "1", required = true)
    private Integer id;

    @ApiModelProperty(value = "分类Id", name = "categoryId", example = "1", required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "产品名称", name = "name", example = "三星Fold", required = true)
    private String name;

    @ApiModelProperty(value = "子标题", name = "subtitle", example = "史上第一款折叠屏手机", required = true)
    private String subtitle;

    @ApiModelProperty(value = "主图片", name = "mainImage", example = "F:/picture/phone.jpg")
    private String mainImage;

    @ApiModelProperty(value = "子图片", name = "subImages", example = "F:/picture/phone2.jpg")
    private String subImages;

    @ApiModelProperty(value = "详情介绍", name = "detail", example = "史上第一款折叠屏手机，惊艳世界的折叠屏先驱", required = true)
    private String detail;

    @ApiModelProperty(value = "价格", name = "price", example = "17000", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "库存", name = "stock", example = "100", required = true)
    private Integer stock;

    @ApiModelProperty(value = "状态", name = "status", example = "1", required = true)
    private Integer status;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    public Product(Integer id, Integer categoryId, String name, String subtitle, String mainImage, String subImages, String detail, BigDecimal price, Integer stock, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.mainImage = mainImage;
        this.subImages = subImages;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Product() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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