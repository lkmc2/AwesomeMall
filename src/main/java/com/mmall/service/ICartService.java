package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车服务接口
 */

public interface ICartService {

    /**
     * 添加商品到购物车
     * @param userId 用户id
     * @param productId 产品id
     * @param count 数量
     * @return 带购物车信息的成功响应
     */
    ServerResponse add(Integer userId, Integer productId, Integer count);
}
