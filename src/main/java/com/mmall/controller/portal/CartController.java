package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 添加产品到购物车
     * @param session 浏览器session
     * @param count 添加数量
     * @param productId 产品id
     * @return 带购物车信息的响应
     */
    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iCartService.add(user.getId(), productId, count); //添加产品到购物车
    }

}
