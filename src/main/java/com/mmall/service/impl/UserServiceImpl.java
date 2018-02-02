package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lkmc2 on 2018/2/2.
 * 用户服务实现类
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper; //连接数据库的用户匹配接口（相当于UserDao）

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username); //检查该用户在数据库中是否存在
        if (resultCount == 0) { //查询结果数量为0
            return ServerResponse.createByErrorMessage("用户名不存在"); //返回错误响应
        }

        //todo 密码登陆MD5

        User user = userMapper.selectLogin(username, password); //查询用户用于登陆
        if (user == null) { //查询到到的用户为空
            return ServerResponse.createByErrorMessage("密码错误"); //返回错误响应
        }

        user.setPassword(StringUtils.EMPTY); //设置密码为空
        return ServerResponse.createBySuccess("登陆成功", user); //返回成功响应
    }
}
