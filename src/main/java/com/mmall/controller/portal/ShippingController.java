package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/12.
 * 地址控制器
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService; //地址服务

    /**
     * 添加地址到数据库
     * @param session 浏览器session
     * @param shipping 地址对象
     * @return 带地址信息的响应
     */
    @RequestMapping("add")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse add(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping); //添加地址到数据库
    }

    /**
     * 删除数据库中的地址
     * @param session 浏览器session
     * @param shippingId 地址id
     * @return 带是否删除成功信息的响应
     */
    @RequestMapping("del")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse del(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId); //根据用户id和地址id删除地址
    }

    /**
     * 更新数据库中的地址
     * @param session 浏览器session
     * @param shipping 地址对象
     * @return 是否更新成功信息的响应
     */
    @RequestMapping("update")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse update(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping); //根据用户id和地址id更新地址
    }

    /**
     * 查询数据库中的地址
     * @param session 浏览器session
     * @param shippingId 地址id
     * @return 带地址信息的响应
     */
    @RequestMapping("select")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId); //根据用户id和地址id查询地址
    }

    /**
     * 查询某个用户的所有地址
     * @param pageNum 当前页数
     * @param pageSize 页面展示多少条地址信息
     * @param session 浏览器session
     * @return 带地址信息的响应
     */
    @RequestMapping("list")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize); //根据用户id查询所有地址信息
    }
}
