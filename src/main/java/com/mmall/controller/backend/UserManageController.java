package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/7.
 * 用户管理控制器（后台）
 */

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService; //用户服务Service

    /**
     * 管理员登陆
     * @param username 用户名
     * @param password 密码
     * @param session 浏览器session
     * @return 是否登陆成功
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password); //登陆获取响应
        if (response.isSuccess()) { //登陆成功
            User user = response.getData(); //获取响应中的用户数据
            if (user.getRole() == Const.Role.ROLE_ADMIN) { //用户角色为管理员
                session.setAttribute(Const.CURRENT_USER, user); //将用户数据设置到session
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
