package com.mmall.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "订单对象", description = "这是订单对象")
public class Order {
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(value = "订单号", name = "orderNo", example = "1491753014256", required = true)
    private Long orderNo;

    @ApiModelProperty(value = "用户id", name = "userId", example = "13", required = true)
    private Integer userId;

    @ApiModelProperty(value = "收货地址id", name = "shippingId", example = "4", required = true)
    private Integer shippingId;

    @ApiModelProperty(value = "实际支付金额", name = "payment", example = "23.50", required = true)
    private BigDecimal payment;

    @ApiModelProperty(value = "支付类型", name = "paymentType", example = "1", required = true)
    private Integer paymentType;

    @ApiModelProperty(value = "运费", name = "postage", example = "15", required = true)
    private Integer postage;

    @ApiModelProperty(value = "支付状态", name = "status", example = "10", required = true)
    private Integer status;

    @ApiModelProperty(value = "支付时间", name = "paymentTime", example = "2019-04-13 21:42:40", required = true)
    private Date paymentTime;

    @ApiModelProperty(value = "发货时间", name = "sendTime", example = "2019-04-14 21:42:40", required = true)
    private Date sendTime;

    @ApiModelProperty(value = "交易完成时间", name = "sendTime", example = "2019-04-15 21:42:40", required = true)
    private Date endTime;

    @ApiModelProperty(value = "交易关闭时间", name = "closeTime", example = "2019-04-16 21:42:40", required = true)
    private Date closeTime;

    @ApiModelProperty(value = "订单创建时间", name = "createTime", example = "2019-04-13 21:42:40", required = true)
    private Date createTime;

    @ApiModelProperty(value = "订单更新时间", name = "updateTime", example = "2019-04-15 21:42:40", required = true)
    private Date updateTime;

    public Order(Integer id, Long orderNo, Integer userId, Integer shippingId, BigDecimal payment, Integer paymentType, Integer postage, Integer status, Date paymentTime, Date sendTime, Date endTime, Date closeTime, Date createTime, Date updateTime) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.shippingId = shippingId;
        this.payment = payment;
        this.paymentType = paymentType;
        this.postage = postage;
        this.status = status;
        this.paymentTime = paymentTime;
        this.sendTime = sendTime;
        this.endTime = endTime;
        this.closeTime = closeTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Order() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
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