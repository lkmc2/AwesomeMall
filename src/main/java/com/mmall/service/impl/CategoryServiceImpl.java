package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by lkmc2 on 2018/2/9.
 * 分类服务实现类
 */

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class); //日志打印器

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

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        //根据父级分类id获取分类子节点
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        if (CollectionUtils.isEmpty(categoryList)) { //列表为空
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList); //返回成功的响应
    }

    @Override
    public ServerResponse selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet(); //新建HashSet
        findChildCategory(categorySet, categoryId); //递归算法，算出子节点

        List<Integer> categoryIdList = Lists.newArrayList(); //新建 ArrayList
        if (categoryId != null) { //分类id非空
            for (Category categoryItem : categorySet) { //遍历分类集合
                categoryIdList.add(categoryItem.getId()); //添加子节点id到列表中
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);  //返回带子节点id列表的响应
    }
    //递归算法，算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId); //从数据库中找到该分类id对应的分类
        if (category != null) { //该分类非空
            categorySet.add(category); //添加到set集合
        }
        //查找子节点的退出条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);//根据父节点id选择其子节点
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet; //返回存放子节点的set集合
    }
}
