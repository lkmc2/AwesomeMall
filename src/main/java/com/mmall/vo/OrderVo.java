package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单VO对象
 */
@ApiModel(value = "订单VO对象", description = "这是订单VO对象")
public class OrderVo {

    @ApiModelProperty(value = "订单号", name = "orderNo", example = "1491753014256", required = true)
    private Long orderNo;

    @ApiModelProperty(value = "实际支付金额", name = "payment", example = "23.50", required = true)
    private BigDecimal payment;

    @ApiModelProperty(value = "支付类型", name = "paymentType", example = "1", required = true)
    private Integer paymentType;

    @ApiModelProperty(value = "支付描述", name = "paymentTypeDesc", example = "这是支付描述", required = true)
    private String paymentTypeDesc;

    @ApiModelProperty(value = "运费", name = "postage", example = "15", required = true)
    private Integer postage;

    @ApiModelProperty(value = "订单状态", name = "status", example = "10", required = true)
    private Integer status;

    @ApiModelProperty(value = "支付状态描述", name = "statusDesc", example = "这是支付状态描述", required = true)
    private String statusDesc;

    @ApiModelProperty(value = "支付时间", name = "paymentTime", example = "2019-04-13 21:42:40", required = true)
    private String paymentTime;

    @ApiModelProperty(value = "发货时间", name = "sendTime", example = "2019-04-14 21:42:40", required = true)
    private String sendTime;

    @ApiModelProperty(value = "交易完成时间", name = "sendTime", example = "2019-04-15 21:42:40", required = true)
    private String endTime;

    @ApiModelProperty(value = "交易关闭时间", name = "closeTime", example = "2019-04-16 21:42:40", required = true)
    private String closeTime;

    @ApiModelProperty(value = "订单创建时间", name = "createTime", example = "2019-04-13 21:42:40", required = true)
    private String createTime;

    @ApiModelProperty(value = "订单子项列表明细", name = "orderItemVoList", required = true)
    private List<OrderItemVo> orderItemVoList;

    @ApiModelProperty(value = "图片主机名", name = "imageHost", example = "localhost", required = true)
    private String imageHost;

    @ApiModelProperty(value = "收货地址id", name = "shippingId", example = "4", required = true)
    private Integer shippingId;

    @ApiModelProperty(value = "收货人姓名", name = "receiverName", example = "王晓芳", required = true)
    private String receiverName;

    private ShippingVo shippingVo; //地址对象

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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

    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public ShippingVo getShippingVo() {
        return shippingVo;
    }

    public void setShippingVo(ShippingVo shippingVo) {
        this.shippingVo = shippingVo;
    }
}
