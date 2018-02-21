package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/21.
 * 订单管理控制器（后台）
 */

@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService; //用户服务Service

    @Autowired
    private IOrderService iOrderService; //订单服务Service

    /**
     * 获取用户所有订单信息
     * @param session 浏览器session
     * @param pageNum 页号
     * @param pageSize 页面展示条数
     * @return 返回带分页信息的响应
     */
    @RequestMapping("list.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 查询订单详情
     * @param session 浏览器session
     * @param orderNo 订单号
     * @return 带订单值对象的响应
     */
    @RequestMapping("detail.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 搜索订单
     * @param session 浏览器session
     * @param orderNo 订单号
     * @param pageNum 页号
     * @param pageSize 页面展示条数
     * @return 带分页信息的响应
     */
    @RequestMapping("search.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 设置订单发货
     * @param session 浏览器session
     * @param orderNo 订单号
     * @return 是否发货成功
     */
    @RequestMapping("send_goods.do")
    @ResponseBody //使返回值自动使用json序列化
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
