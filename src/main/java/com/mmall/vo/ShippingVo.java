package com.mmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lkmc2 on 2018/2/21.
 * 收获地址值对象
 */
@ApiModel(value = "收货地址VO对象", description = "这是收货地址VO对象")
public class ShippingVo {

    @ApiModelProperty(value = "收货人姓名", name = "receiverName", example = "林悦", required = true)
    private String receiverName;

    @ApiModelProperty(value = "收货人电话", name = "receiverPhone", example = "7350443")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人手机号", name = "receiverMobile", example = "13800001234", required = true)
    private String receiverMobile;

    @ApiModelProperty(value = "省份", name = "receiverProvince", example = "广西省", required = true)
    private String receiverProvince;

    @ApiModelProperty(value = "城市", name = "receiverCity", example = "南宁", required = true)
    private String receiverCity;

    @ApiModelProperty(value = "地区", name = "receiverDistrict", example = "西乡塘区", required = true)
    private String receiverDistrict;

    @ApiModelProperty(value = "收货地址", name = "receiverAddress", example = "潇湘大楼327号", required = true)
    private String receiverAddress;

    @ApiModelProperty(value = "邮编", name = "receiverZip", example = "530200", required = true)
    private String receiverZip;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }
}
