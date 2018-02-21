package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

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

    /**
     * 根据产品id获取产品值详情
     * @param productId 产品id
     * @return 带产品值详情的响应
     */
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 获取产品列表
     * @param pageNum 当前页号
     * @param pageSize 展示产品条数
     * @return 产品列表
     */
    ServerResponse getProductList(int pageNum, int pageSize);

    /**
     * 根据产品名或者产品id搜索产品
     * @param productName 产品名
     * @param productId 产品id
     * @param pageNum 当前页数
     * @param pageSize 展示产品数量
     * @return 是否搜索成功
     */
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 根据id获取产品详情
     * @param productId 产品id
     * @return 带产品详情的响应
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);


    /**
     * 通过关键词或者分类获取产品
     * @param keyword 关键词
     * @param categoryId 分类id
     * @param pageNum 当前页数
     * @param pageSize 每页展示的产品数量
     * @return 带页面信息的响应
     */
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                         int pageNum, int pageSize, String orderBy);
}
