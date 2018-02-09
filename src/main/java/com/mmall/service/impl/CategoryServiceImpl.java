package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lkmc2 on 2018/2/9.
 * 分类服务实现类
 */

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper; //连接数据库的分类匹配接口（相当于UserDao）

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) { //父节点id为空，或分类名为空
            return ServerResponse.createByErrorMessage("添加商品分类参数错误");
        }

        Category category = new Category(); //商品分类对象
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true); //该分类是可用的

        int rowCount = categoryMapper.insert(category); //将分类插入数据库
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加商品种类成功");
        }
        return ServerResponse.createByErrorMessage("添加商品种类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) { //分类id为空或分类名为空
            return ServerResponse.createByErrorMessage("更新商品种类参数错误");
        }

        Category category = new Category(); //商品分类对象
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category); //根据id选择性更新商品种类
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新商品种类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新商品种类名字失败");
    }
}
