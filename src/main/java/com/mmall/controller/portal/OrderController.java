package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单控制器
 */

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService; //订单服务接口

    /**
     * 请求支付并生成二维码
     * @param session 浏览器session
     * @param orderNo 订单号
     * @param request 浏览器请求
     * @return 带参数信息的响应
     */
    @RequestMapping("pay.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传文件夹路径
        return iOrderService.pay(orderNo, user.getId(), path); //请求支付并生成二维码
    }

}
