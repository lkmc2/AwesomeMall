package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品管理控制器（后台）
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService; //用户服务接口

    @Autowired
    private IProductService iProductService; //产品服务接口

    /**
     * 保存产品（已存在则更新，未存在则插入）
     * @param session 浏览器session
     * @param product 产品
     * @return 是否保存成功
     */
    @RequestMapping("save.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iProductService.saveOrUpdateProduct(product); //执行更新或添加产品
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 设置商品销售状态
     * @param session 浏览器session
     * @param productId 产品id
     * @param status 销售状态
     * @return 设置状态是否成功
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iProductService.setSaleStatus(productId, status); //设置产品销售状态
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 根据id获取产品详情
     * @param session 浏览器详情
     * @param productId 产品id
     * @return 产品详情
     */
    @RequestMapping("detail.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iProductService.manageProductDetail(productId); //获取产品详情
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
