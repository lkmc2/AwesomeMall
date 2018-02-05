package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by lkmc2 on 2018/2/2.
 * 用户服务接口
 */
public interface IUserService {
    /**
     * 登陆方法
     * @param username 用户名
     * @param password 用户密码
     * @return 服务响应
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 注册方法
     * @param user 用户
     * @return 服务响应
     */
    ServerResponse<String> register(User user);

    /**
     * 检查参数是否合法
     * @param str 参数
     * @param type 参数类型
     * @return 参数是否合法
     */
    ServerResponse<String> checkValid(String str, String type);
}
