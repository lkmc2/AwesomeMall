package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
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

        String md5Password = MD5Util.MD5EncodeUtf8(password); //将用户密码进行md5加密

        User user = userMapper.selectLogin(username, md5Password); //查询用户用于登陆
        if (user == null) { //查询到到的用户为空
            return ServerResponse.createByErrorMessage("密码错误"); //返回错误响应
        }

        user.setPassword(StringUtils.EMPTY); //设置密码为空
        return ServerResponse.createBySuccess("登陆成功", user); //返回成功响应
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME); //检查用户名是否合法
        if (!validResponse.isSuccess()) { //用户名不合法
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL); //检查邮箱是否合法
        if (!validResponse.isSuccess()) { //邮箱不合法
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER); //设置用户为普通用户
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword())); //MD5加密并给用户设置密码

        int resultCount = userMapper.insert(user); //将用户插入数据库
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNoneBlank(type)) { //用户名非空非空格
            if (Const.USERNAME.equals(type)) { //类型为用户名
                int resultCount = userMapper.checkUsername(str); //检查用户名是否存在
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) { //类型为邮箱
                int resultCount = userMapper.checkEmail(str); //检查邮箱是否存在
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        } else { //参数为空
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("检验成功");
    }
}