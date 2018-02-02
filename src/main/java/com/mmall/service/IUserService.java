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
}
