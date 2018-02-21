package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by lkmc2 on 2018/2/12.
 * 地址服务接口
 */

public interface IShippingService {
    /**
     * 添加地址
     * @param userId 用户id
     * @param shipping 地址对象
     * @return 带地址信息的响应
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 根据用户id和地址id删除地址
     * @param userId 用户id
     * @param shippingId 地址id
     * @return 带是否删除成功信息的响应
     */
    ServerResponse<String> del(Integer userId, Integer shippingId);

    /**
     * 更新地址信息
     * @param userId 用户id
     * @param shipping 地址对象
     * @return 带更新信息的响应
     */
    ServerResponse update(Integer userId, Shipping shipping);

    /**
     * 根据用户id和地址id查询地址
     * @param userId 用户id
     * @param shippingId 地址id
     * @return 带地址信息的响应
     */
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    /**
     * 根据用户id查询所有地址信息
     * @param userId 用户id
     * @param pageNum 当前页号
     * @param pageSize 页面展示多少条地址
     * @return 带页面信息的成功响应
     */
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
