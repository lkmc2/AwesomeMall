package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/9.
 * 分类管理控制器
 */

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService; //用户服务接口

    @Autowired
    private ICategoryService iCategoryService; //商品分类服务接口

    /**
     * 添加分类
     * @param session 浏览器session
     * @param categoryName 分类名
     * @param parentId 父节点id
     * @return 是否添加成功
     */
    @RequestMapping("add_category")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse addCategory(HttpSession session, String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        //校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iCategoryService.addCategory(categoryName, parentId); //添加商品分类到数据库
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 设置分类名称
     * @param session 浏览器session
     * @param categoryId 分类id
     * @param categoryName 分类名
     * @return 设置分类是否成功
     */
    @RequestMapping("set_category_name")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        //校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iCategoryService.updateCategoryName(categoryId, categoryName); //更新商品分类名
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 查询子节点的分类信息，并且不递归，保持平级
     * @param session 浏览器session
     * @param categoryId 分类id
     * @return 带子节点数据的响应
     */
    @RequestMapping("get_category")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        //校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            //查询子节点的category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 查询子节点并遍历子节点
     * @param session 浏览器session
     * @param categoryId 分类id
     * @return 带子节点数据的响应
     */
    @RequestMapping("get_deep_category")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse getChildrenAndDeepChildrenCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //获取session中的用户信息
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }

        //校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            //查询当前节点的id，并递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}
