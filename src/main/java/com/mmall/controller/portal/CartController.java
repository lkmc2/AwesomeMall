package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车控制器
 */

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService; //购物车服务接口

    /**
     * 查询并返回购物车信息
     * @param session 浏览器session
     * @return 带购物车信息的响应
     */
    @RequestMapping("list")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId()); //查询购物车中的产品
    }

    /**
     * 添加产品到购物车
     * @param session 浏览器session
     * @param count 添加数量
     * @param productId 产品id
     * @return 带购物车信息的响应
     */
    @RequestMapping("add")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iCartService.add(user.getId(), productId, count); //添加产品到购物车
    }

    /**
     * 更新购物车中的产品
     * @param session 浏览器session
     * @param count 数量
     * @param productId 产品id
     * @return 带购物车信息的响应
     */
    @RequestMapping("update")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count); //更新购物车中的产品
    }

    /**
     * 删除购物车中对应的产品
     * @param session 浏览器session
     * @param productIds 产品多个id
     * @return 带购物车信息的响应
     */
    @RequestMapping("delete_product")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> deleteProduct(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds); //删除购物车中对应的产品
    }

    /**
     * 全选购物车中的产品
     * @param session 浏览器session
     * @return 带购物车信息的响应
     */
    @RequestMapping("select_all")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED); //设置购物车全选
    }

    /**
     * 全反选购物车中的产品
     * @param session 浏览器session
     * @return 带购物车信息的响应
     */
    @RequestMapping("un_select_all")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED); //设置购物车全反选
    }

    /**
     * 选择购物车中的某项
     * @param session 浏览器session
     * @param productId 产品id
     * @return 带购物车信息的响应
     */
    @RequestMapping("select")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> unSelectAll(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED); //设置购物车全反选
    }

    /**
     * 取消选择购物车中的某项
     * @param session 浏览器session
     * @param productId 产品id
     * @return 带购物车信息的响应
     */
    @RequestMapping("un_select")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<CartVo> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED); //设置购物车取消选择某项
    }

    /**
     * 根据用户id获取购物车中所有产品的数量
     * @param session 浏览器session
     * @param productId 产品id
     * @return 带所有产品数的响应
     */
    @RequestMapping("get_cart_product_count")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<Integer> get(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createBySuccess(0); //返回成功的响应，表述购物车数量为0
        }
        return iCartService.getCartProductCount(user.getId()); //根据用户id获取购物车中所有产品的数量
    }


}
