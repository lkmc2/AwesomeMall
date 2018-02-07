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

    String selectQuestionByUsername(String username); //通过用户名选择找回密码的问题

    //检查找回密码问题的答案是否正确
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    //通过用户名修改密码
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    //检查用户密码是否正确
    int checkPassword(@Param("password")String password, @Param("userId")Integer userId);

    //通过用户id检查邮箱是否已被他人占用
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}