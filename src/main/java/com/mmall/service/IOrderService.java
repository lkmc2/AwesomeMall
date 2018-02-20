package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单服务接口
 */

public interface IOrderService {
    /**
     * 请求支付并生成二维码
     * @param orderNo 订单号
     * @param userId 用户id
     * @param path 上传文件路径
     * @return 带参数信息的响应
     */
    ServerResponse pay(Long orderNo, Integer userId, String path);
}
