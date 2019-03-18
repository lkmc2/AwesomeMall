package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品列表Value Object
 */
@ApiModel(value = "产品列表VO对象", description = "这是产品列表VO对象")
public class ProductListVo {

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

    @ApiModelProperty(value = "价格", name = "price", example = "17000", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "状态", name = "status", example = "1", required = true)
    private Integer status;

    @ApiModelProperty(value = "图片服务器url前缀", name = "imageHost", example = "localhost", required = true)
    private String imageHost;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
