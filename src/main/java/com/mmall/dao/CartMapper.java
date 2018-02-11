package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //使用用户id和产品id查询购物车
    Cart selectCartByUserIdProductId(@Param("userId")Integer userId, @Param("productId")Integer productId);

    List<Cart> selectCartByUserId(Integer userId); //通过用户id选择购物车

    //通过用户id查询产品的勾选状态
    int selectCartProductCheckedStatusByUserId(Integer userId);
}