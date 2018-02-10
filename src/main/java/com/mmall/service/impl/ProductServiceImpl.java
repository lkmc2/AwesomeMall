package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品服务实现类
 */

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper; //连接数据库的产品匹配接口（相当于Dao）

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) { //产品非空
            if (StringUtils.isBlank(product.getSubImages())) { //产品子图非空
                String[] subImageArray = product.getSubImages().split(","); //分割获取子图数组
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]); //将主图设置为子图的第一项
                }
            }

            if (product.getId() != null) { //产品id非空，执行更新操作
                int rowCount = productMapper.updateByPrimaryKey(product); //根据id更新产品
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                } else {
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            } else { //产品id为空，执行插入操作
                int rowCount = productMapper.insert(product); //插入新产品
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                } else {
                    return ServerResponse.createByErrorMessage("新增产品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) { //产品id或状态为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowCount = productMapper.updateByPrimaryKeySelective(product); //根据id选择性更新产品
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }
}
