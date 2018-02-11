package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

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
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车信息
     * @param userId 用户id
     * @param productId 产品id
     * @param count 数量
     * @return 返回带购物车信息的成功响应
     */
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车中的产品
     * @param userId 用户id
     * @param productIds 产品多个id
     * @return 返回带购物车信息的成功响应
     */
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    /**
     * 查询购物车中的信息
     * @param userId 用户id
     * @return 带购物车信息的响应
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * 选择或不选购物车中的产品
     * @param userId 用户id
     * @param productId 产品id
     * @param checked 需要设置的选中状态
     * @return 带购物车信息的响应
     */
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    /**
     * 获取购物车中的产品总数
     * @param userId 用户id
     * @return 带购物车中的产品总数的响应
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
