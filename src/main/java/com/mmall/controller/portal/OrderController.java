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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单控制器
 */

@Api(value = "订单管理的接口", tags = {"订单管理的Controller"})
@RestController
@RequestMapping("/order/")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class); //日志打印器

    @Autowired
    private IOrderService iOrderService; //订单服务接口


    @ApiOperation(value = "创建订单", notes = "创建订单")
    @ApiImplicitParam(name = "shippingId", value = "送货地址id", required = true, dataType = "int", paramType = "query")
    @PostMapping("create")
    public ServerResponse create(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId); //创建订单
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("cancel")
    public ServerResponse cancel(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(user.getId(), orderNo); //取消订单
    }

    @ApiOperation(value = "获取购物车中已选中的商品详情", notes = "获取购物车中已选中的商品详情")
    @PostMapping("get_order_cart_product")
    public ServerResponse getOrderCartProduct(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId()); //获取购物车中已选中的商品详情
    }

    @ApiOperation(value = "获取订单详情", notes = "获取订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("detail")
    public ServerResponse detail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo); //获取订单详情
    }

    @ApiOperation(value = "列出用户的所有订单信息", notes = "列出用户的所有订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面展示条数", dataType = "int", paramType = "query")
    })
    @PostMapping("list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNum, pageSize); //获取订单列表的分页信息
    }

    @ApiOperation(value = "请求支付并生成二维码", notes = "请求支付并生成二维码")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("pay")
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传文件夹路径
        return iOrderService.pay(orderNo, user.getId(), path); //请求支付并生成二维码
    }

    @ApiOperation(value = "支付宝回调方法", notes = "支付宝回调方法")
    @PostMapping("callback")
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap(); //新建参数map

        Map requestParams = request.getParameterMap(); //从request中获取支付宝的参数
        for (Object obj : requestParams.keySet()) { //使用迭代器遍历
            String name = (String) obj; //获取key
            String[] values = (String[]) requestParams.get(name); //获取value
            String valueStr = "";

            for (int i = 0; i < values.length; i++) { //用逗号对values的内容进行拼接
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr); //将参数放入map
        }
        LOGGER.info("支付宝回调，sign:{}，trade_status:{}，参数:{}",
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
            LOGGER.error("支付宝验证回调异常");
            e.printStackTrace();
        }

        //todo 验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params); //执行支付宝回调方法
        if (serverResponse.isSuccess()) { //响应成功
            return Const.AlipayCallback.RESPONSE_SUCCESS; //成功响应
        }
        return Const.AlipayCallback.RESPONSE_FAILED; //失败响应
    }

    @ApiOperation(value = "查询订单状态", notes = "查询订单状态")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "Long", paramType = "query")
    @PostMapping("query_order_pay_status")
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

