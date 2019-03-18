package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/2.
 * 用户控制器
 */
@Api(value = "用户管理的接口", tags = {"用户管理的Controller"})
@RestController
@RequestMapping("/user/") //请求路径
public class UserController {

    @Autowired
    private IUserService iUserService; //用户服务Service


    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("login")
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        //service-->mybatis.dao
        ServerResponse<User> response = iUserService.login(username, password); //登陆获取服务响应
        if (response.isSuccess()) { //服务响应正确
            session.setAttribute(Const.CURRENT_USER, response.getData()); //设置当前登陆用户的数据
        }
        return response; //返回响应
    }

    @ApiOperation(value = "用户退出登陆", notes = "用户退出登陆")
    @PostMapping("logout")
    public ServerResponse<User> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER); //从session中移除当前用户
        return ServerResponse.createBySuccess(); //返回成功的响应
    }

    @ApiOperation(value = "用户退出登陆", notes = "用户退出登陆")
    @PostMapping("register")
    public ServerResponse<String> register(@RequestBody User user) {
        return iUserService.register(user);
    }

    @ApiOperation(value = "检查参数是否合法", notes = "检查参数是否合法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "参数", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "参数类型", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("check_valid")
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @ApiOperation(value = "从session中获取用户数据", notes = "从session中获取用户数据")
    @PostMapping("get_user_info")
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户数据
        if (user != null) { //用户数据非空
            return ServerResponse.createBySuccess(user); //正确响应
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @ApiOperation(value = "忘记密码，通过用户名获取找回密码的问题", notes = "忘记密码，通过用户名获取找回密码的问题")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query")
    @PostMapping("forget_get_question")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username); //通过用户名获取找回密码的问题
    }

    @ApiOperation(value = "检查用户填写的找回密码的问题答案是否正确", notes = "检查用户填写的找回密码的问题答案是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "question", value = "找回密码问题", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "answer", value = "用户填写的答案", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("forget_check_answer")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @ApiOperation(value = "用户忘记密码后重置密码", notes = "用户忘记密码后重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "passwordNew", value = "新密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "forgetToken", value = "服务器给该账号的Token认证", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("forget_reset_password")
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @ApiOperation(value = "用户登陆状态下的重置密码", notes = "用户登陆状态下的重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passwordOld", value = "旧密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "passwordNew", value = "新密码", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("reset_password")
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户数据
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user); //重置用户密码
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PostMapping("update_information")
    public ServerResponse<User> updateInformation(HttpSession session, @RequestBody User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户数据
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId()); //为user设置缓存中的用户id
        user.setUsername(currentUser.getUsername()); //用户名不能被更新，从session中获取

        ServerResponse<User> response = iUserService.updateInformation(user); //更新用户信息
        if (response.isSuccess()) { //更新用户信息成功
            session.setAttribute(Const.CURRENT_USER, response.getData()); //将session的用户信息更新
        }
        return response;
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @PostMapping("get_information")
    public ServerResponse<User> getInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户信息
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登陆status=10");
        }
        return iUserService.getInformation(currentUser.getId()); //获取用户信息
    }

}
