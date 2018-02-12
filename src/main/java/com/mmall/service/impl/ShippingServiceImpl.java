package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lkmc2 on 2018/2/12.
 * 地址服务实现类
 */

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper; //连接数据库的地址匹配器（相当于Dao）

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId); //设置用户id
        int rowCount = shippingMapper.insert(shipping); //在数据库中插入新地址
        if (rowCount > 0) {
            Map result = Maps.newHashMap(); //新建Map
            result.put("shippingId", shipping.getId()); //将插入后的地址id设置到map中
            return ServerResponse.createBySuccess("新建地址成功", result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId); //根据用户id和地址id删除数据库中的地址
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId); //设置用户id
        int rowCount = shippingMapper.updateByShipping(shipping); //根据地址id和用户id更新地址信息
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId); //根据用户id和地址id查询地址
        if (shipping == null) { //地址为空
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); //开始分页
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId); //根据用户id选择该用户所有地址
        PageInfo pageInfo = new PageInfo(shippingList); //创建页面信息
        return ServerResponse.createBySuccess(pageInfo); //返回带页面信息的成功响应
    }
}
