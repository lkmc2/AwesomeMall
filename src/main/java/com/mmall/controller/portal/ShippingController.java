package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by lkmc2 on 2018/2/12.
 * 地址控制器
 */
@Api(value = "地址管理的接口", tags = {"地址管理的Controller"})
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService; //地址服务

    @ApiOperation(value = "添加地址到数据库", notes = "添加地址到数据库")
    @PostMapping("add")
    public ServerResponse add(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping); //添加地址到数据库
    }

    @ApiOperation(value = "删除数据库中的地址", notes = "删除数据库中的地址")
    @ApiImplicitParam(name = "shippingId", value = "地址id", required = true, dataType = "int", paramType = "query")
    @PostMapping("del")
    public ServerResponse del(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId); //根据用户id和地址id删除地址
    }

    @ApiOperation(value = "更新数据库中的地址", notes = "更新数据库中的地址")
    @PostMapping("update")
    public ServerResponse update(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping); //根据用户id和地址id更新地址
    }

    @ApiOperation(value = "查询数据库中的地址", notes = "查询数据库中的地址")
    @ApiImplicitParam(name = "shippingId", value = "地址id", required = true, dataType = "int", paramType = "query")
    @PostMapping("select")
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId); //根据用户id和地址id查询地址
    }

    @ApiOperation(value = "查询某个用户的所有地址", notes = "查询某个用户的所有地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面展示多少条地址信息", dataType = "int", paramType = "query")
    })
    @PostMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户信息
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize); //根据用户id查询所有地址信息
    }

}
