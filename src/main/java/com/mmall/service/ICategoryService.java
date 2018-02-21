package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

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

    /**
     * 查询子节点的分类信息，并且不递归，保持平级
     * @param categoryId 分类id
     * @return 是否获取子节点成功
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询本节点的id和孩子节点的id
     * @param categoryId 分类id
     * @return 带子节点id列表的响应
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
