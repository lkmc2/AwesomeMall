package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单控制器
 */

@Controller
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class); //日志打印器

    @Autowired
    private IOrderService iOrderService; //订单服务接口

    /**
     * 创建订单
     * @param session 浏览器session
     * @param shippingId 送货地址id
     * @return 带结果参数的响应
     */
    @RequestMapping("create.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse create(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId); //创建订单
    }

    /**
     * 取消订单
     * @param session 浏览器session
     * @param orderNo 订单号
     * @return 带结果参数的响应
     */
    @RequestMapping("cancel.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse cancel(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(user.getId(), orderNo); //取消订单
    }

    /**
     * 获取购物车中已选中的商品详情
     * @param session 浏览器session
     * @return 带订单产品值对象的响应
     */
    @RequestMapping("get_order_cart_product.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse getOrderCartProduct(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId()); //获取购物车中已选中的商品详情
    }

    /**
     * 获取订单详情
     * @param session 浏览器session
     * @param orderNo 订单号
     * @return 返回带订单值对象的响应
     */
    @RequestMapping("detail.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse detail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo); //获取订单详情
    }

    /**
     * 列出用户的所有订单信息
     * @param session 浏览器session
     * @param pageNum 页号
     * @param pageSize 页面展示条数
     * @return 带分页信息的响应
     */
    @RequestMapping("list.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNum, pageSize); //获取订单列表的分页信息
    }

    /**
     * 请求支付并生成二维码
     *
     * @param session 浏览器session
     * @param orderNo 订单号
     * @param request 浏览器请求
     * @return 带参数信息的响应
     */
    @RequestMapping("pay.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传文件夹路径
        return iOrderService.pay(orderNo, user.getId(), path); //请求支付并生成二维码
    }


    /**
     * 支付宝回调方法
     * @param request 浏览器请求
     * @return 是否回调成功
     */
    @RequestMapping("callback.do")
    @ResponseBody //使返回值自动使用json序列化
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap(); //新建参数map

        Map requestParams = request.getParameterMap(); //从request中获取支付宝的参数
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) { //使用迭代器遍历
            String name = (String) iter.next(); //获取key
            String[] values = (String[]) requestParams.get(name); //获取value
            String valueStr = "";

            for (int i = 0; i < values.length; i++) { //用逗号对values的内容进行拼接
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr); //将参数放入map
        }
        logger.info("支付宝回调，sign:{}，trade_status:{}，参数:{}",
                params.get("sign"), params.get("trade_status"), params.toString());

        //验证回调的正确性，并避免重复通知
        params.remove("sign_type"); //移除签名类型（必要操作）
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params,
                    Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType()); //验证支付宝签名

            if (!alipayRSACheckedV2) { //验证不通过
                return ServerResponse.createByErrorMessage("非法请求，验证不通过，再恶意请求将报警");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常");
            e.printStackTrace();
        }

        //todo 验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params); //执行支付宝回调方法
        if (serverResponse.isSuccess()) { //响应成功
            return Const.AlipayCallback.RESPONSE_SUCCESS; //成功响应
        }
        return Const.AlipayCallback.RESPONSE_FAILED; //失败响应
    }

    /**
     * 查询订单状态
     * @param session 浏览器session
     * @param orderNo 订单号
     * @return 是否交易成功
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        //查询订单支付状态
        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccess()) { //响应成功
            return ServerResponse.createBySuccess(true); //返回成功的响应
        }
        return ServerResponse.createBySuccess(false); //返回失败的响应
    }
}

