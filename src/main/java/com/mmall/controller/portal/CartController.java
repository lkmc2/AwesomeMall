package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车控制器
 */
@Api(value = "购物车管理的接口", tags = {"购物车管理的Controller"})
@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService; //购物车服务接口


    @ApiOperation(value = "查询并返回购物车信息", notes = "查询并返回购物车信息")
    @PostMapping("list")
    public ServerResponse<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId()); //查询购物车中的产品
    }

    @ApiOperation(value = "添加产品到购物车", notes = "添加产品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "添加数量", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("add")
    public ServerResponse<CartVo> add(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iCartService.add(user.getId(), productId, count); //添加产品到购物车
    }

    @ApiOperation(value = "更新购物车中的产品", notes = "更新购物车中的产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "添加数量", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("update")
    public ServerResponse<CartVo> update(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count); //更新购物车中的产品
    }

    @ApiOperation(value = "删除购物车中对应的产品", notes = "删除购物车中对应的产品")
    @ApiImplicitParam(name = "productIds", value = "产品多个id", required = true, dataType = "String", paramType = "query")
    @PostMapping("delete_product")
    public ServerResponse<CartVo> deleteProduct(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds); //删除购物车中对应的产品
    }

    @ApiOperation(value = "全选购物车中的产品", notes = "全选购物车中的产品")
    @PostMapping("select_all")
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED); //设置购物车全选
    }

    @ApiOperation(value = "全反选购物车中的产品", notes = "全反选购物车中的产品")
    @PostMapping("un_select_all")
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED); //设置购物车全反选
    }

    @ApiOperation(value = "选择购物车中的某项", notes = "选择购物车中的某项")
    @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "int", paramType = "query")
    @PostMapping("select")
    public ServerResponse<CartVo> unSelectAll(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED); //设置购物车全反选
    }

    @ApiOperation(value = "取消选择购物车中的某项", notes = "取消选择购物车中的某项")
    @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "int", paramType = "query")
    @PostMapping("un_select")
    public ServerResponse<CartVo> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED); //设置购物车取消选择某项
    }

    @ApiOperation(value = "根据用户id获取购物车中所有产品的数量", notes = "根据用户id获取购物车中所有产品的数量")
    @PostMapping("get_cart_product_count")
    public ServerResponse<Integer> get(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createBySuccess(0); //返回成功的响应，表述购物车数量为0
        }
        return iCartService.getCartProductCount(user.getId()); //根据用户id获取购物车中所有产品的数量
    }

}
