package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品服务接口
 */


public interface IProductService {

    /**
     * 保存或者更新产品
     * @param product 产品
     * @return 保存或更新成功
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 设置产品销售状态
     * @param productId 产品id
     * @param status 销售状态
     * @return 是否设置成功
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
}
