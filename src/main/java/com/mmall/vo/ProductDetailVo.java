package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品详情Value Object
 */
@ApiModel(value = "产品详情VO对象", description = "这是产品详情VO对象")
public class ProductDetailVo {
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

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-04-13 21:42:40", required = true)
    private String createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-04-15 21:42:40", required = true)
    private String updateTime;

    @ApiModelProperty(value = "图片服务器url前缀", name = "imageHost", example = "localhost", required = true)
    private String imageHost;

    @ApiModelProperty(value = "父分类id", name = "parentCategoryId", example = "0", required = true)
    private Integer parentCategoryId;

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
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
