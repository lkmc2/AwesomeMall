package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/21.
 * 订单管理控制器（后台）
 */
@Api(value = "后台订单管理的接口", tags = {"后台订单管理的Controller"})
@RestController
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService; //用户服务Service

    @Autowired
    private IOrderService iOrderService; //订单服务Service


    @ApiOperation(value = "获取用户所有订单信息", notes = "获取用户所有订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面展示条数", dataType = "int", paramType = "query")
    })
    @PostMapping("list")
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iOrderService.manageList(pageNum, pageSize); //获取用户所有订单的分页信息（后台）
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("detail")
    public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iOrderService.manageDetail(orderNo); //获取订单详情
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "搜索订单", notes = "搜索订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面展示条数", dataType = "int", paramType = "query")
    })
    @PostMapping("search")
    public ServerResponse<PageInfo> orderSearch(HttpSession session,
                                               Long orderNo,
                                               @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iOrderService.manageSearch(orderNo, pageNum, pageSize); //搜索订单
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "设置订单发货", notes = "设置订单发货")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("send_goods")
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iOrderService.manageSendGoods(orderNo); //设置订单发货
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

}
