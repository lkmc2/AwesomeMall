package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username); //检查登陆的用户名是否存在

    int checkEmail(String email); //检查邮箱存在的数量

    //mabatis中有多个参数需要加@Param注解
    //根据用户名和密码查询用户用于登陆
    User selectLogin(@Param("username") String username, @Param("password") String password);
}