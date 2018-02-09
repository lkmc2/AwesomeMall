package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by lkmc2 on 2018/2/9.
 * 分类服务接口
 */

public interface ICategoryService {

    /**
     * 添加商品分类到数据库
     * @param categoryName 商品分类名
     * @param parentId 父节点id
     * @return 是否添加成功
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新商品分类到数据库
     * @param categoryId 分类id
     * @param categoryName 分类名称
     * @return 是否成功成功
     */
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

}
