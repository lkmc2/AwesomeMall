package com.mmall.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "支付信息对象", description = "这是支付信息对象")
public class PayInfo {
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(value = "用户id", name = "userId", example = "13", required = true)
    private Integer userId;

    @ApiModelProperty(value = "订单号", name = "orderNo", example = "1491753014256", required = true)
    private Long orderNo;

    @ApiModelProperty(value = "支付平台", name = "orderNo", example = "1", required = true)
    private Integer payPlatform;

    @ApiModelProperty(value = "支付宝支付流水号", name = "platformNumber", example = "1DSAF888fdsfsdfj89", required = true)
    private String platformNumber;

    @ApiModelProperty(value = "支付宝支付状态", name = "platformStatus", example = "success", required = true)
    private String platformStatus;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-04-13 21:42:40", required = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-04-15 21:42:40", required = true)
    private Date updateTime;

    public PayInfo(Integer id, Integer userId, Long orderNo, Integer payPlatform, String platformNumber, String platformStatus, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.payPlatform = payPlatform;
        this.platformNumber = platformNumber;
        this.platformStatus = platformStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public PayInfo() {
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

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus == null ? null : platformStatus.trim();
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