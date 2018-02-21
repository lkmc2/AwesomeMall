package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    //根据地址id和用户id删除地址
    int deleteByShippingIdUserId(@Param("userId")Integer userId,
                                 @Param("shippingId")Integer shippingId);

    //根据地址id和用户id更新地址信息
    int updateByShipping(Shipping record);

    //根据地址id和用户id查询地址信息
    Shipping selectByShippingIdUserId(@Param("userId")Integer userId,
                                      @Param("shippingId")Integer shippingId);

    //根据用户id选择地址
    List<Shipping> selectByUserId(Integer userId);
}