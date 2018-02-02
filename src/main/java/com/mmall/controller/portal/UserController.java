package com.mmall.controller.portal;

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
 * Created by lkmc2 on 2018/2/2.
 * 用户控制器
 */
@Controller
@RequestMapping("/user/") //请求路径
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     * @param username 用户名
     * @param password 密码
     * @param session 浏览器session
     * @return 服务响应
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        //service-->mybatis.dao
        ServerResponse<User> response = iUserService.login(username, password); //登陆获取服务响应
        if (response.isSuccess()) { //服务响应正确
            session.setAttribute(Const.CURRENT_USER, response.getData()); //设置当前登陆用户的数据
        }
        return response; //返回响应
    }
}
