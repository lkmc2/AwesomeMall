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

    /**
     * 用户登陆

     * @param session 浏览器session
     * @return 服务响应
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<User> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER); //从session中移除当前用户
        return ServerResponse.createBySuccess(); //返回成功的响应
    }

    /**
     * 用户注册
     * @param user 用户
     * @return 服务响应
     */
    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 检查参数是否合法
     * @param str 参数
     * @param type 参数类型
     * @return 参数是否合法
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 从session中获取用户数据
     * @param session 当前页面的session
     * @return 带用户数据的响应
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户数据
        if (user != null) { //用户数据非空
            return ServerResponse.createBySuccess(user); //正确响应
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    /**
     * 忘记密码，通过用户名获取找回密码的问题
     * @param username 用户名
     * @return 找回密码的问题
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username); //通过用户名获取找回密码的问题
    }

    /**
     * 检查用户填写的找回密码的问题答案是否正确
     * @param username 用户名
     * @param question 找回密码问题
     * @param answer 用户填写的答案
     * @return 答案是否正确
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 用户忘记密码后重置密码
     * @param username 用户名
     * @param passwordNew 新密码
     * @param forgetToken 服务器给该账号的Token认证
     * @return 是否重置成功
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.GET)
    @ResponseBody //指定获取浏览器响应转换成指定的格式(json)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }
}
