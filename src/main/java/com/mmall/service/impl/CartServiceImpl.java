package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/11.
 * 购物车服务实现类
 */

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper; //连接数据库查询的购物车匹配器（相当于Dao）

    @Autowired
    private ProductMapper productMapper; //连接数据库查询的产品匹配器（相当于Dao）

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId); //使用用户id和产品id查询购物车
        if (cart == null) { //购物车为空
            //这个产品不在购物车里，需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count); //设置数量
            cartItem.setChecked(Const.Cart.CHECKED); //设置被勾选
            cartItem.setProductId(productId); //设置产品id
            cartItem.setUserId(userId); //设置用户id

            cartMapper.insert(cartItem); //将购物车插入数据库
        } else {
            //这个产品已经存在购物车中，数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count); //设置新的数量
            cartMapper.updateByPrimaryKeySelective(cart); //选择性更新购物车中的数据
        }

        CartVo cartVo = this.getCartVoLimit(userId); //获取带限制购买数的购物车值对象
        return ServerResponse.createBySuccess(cartVo); //返回带购物车信息的成功响应
    }

    /**
     * 获取带限制购买数的购物车值对象
     * @param userId 用户id
     * @return 带限制购买数的购物车值对象
     */
    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo(); //新建购物车值对象
        List<Cart> cartList = cartMapper.selectCartByUserId(userId); //根据用户id选择购物车
        List<CartProductVo> cartProductVoList = Lists.newArrayList(); //新建列表

        BigDecimal cartTotalPrice = new BigDecimal("0"); //购物车总价对象

        if (CollectionUtils.isNotEmpty(cartList)) { //购物车列表非空
            for (Cart cartItem : cartList) { //遍历购物车列表
                CartProductVo cartProductVo = new CartProductVo(); //新建购物车产品值对象
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId()); //根据id查询产品
                if (product != null) { //产品非空，设置产品相关信息
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());

                    int buyLimitCount = 0; //限制购买数

                    if (product.getStock() >= cartItem.getQuantity()) { //数据库中产品的数量大于购物车中的数量
                        //库存充足时
                        buyLimitCount = cartItem.getQuantity(); //限制购买数设置成购物车中的数量

                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS); //限制产品数量成功
                    } else { //数据库库存少于购物车需要的数量
                        buyLimitCount = product.getStock(); //限制购买数等于数据库中的数量
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL); //限制产品数量失败

                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart(); //新建购物车
                        cartForQuantity.setId(cartItem.getId()); //设置id
                        cartForQuantity.setQuantity(buyLimitCount); //设置购物车数量
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity); //选择性更新购物车中的数量
                    }
                    cartProductVo.setQuantity(buyLimitCount); //设置限制购买数
                    //计算产品总价（单价 * 数量）
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked()); //设置产品是否勾选
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) { //如果产品被勾选，将价格添加到购物车总价
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }

                cartProductVoList.add(cartProductVo); //将购物车产品值对象添加到列表
            }
        }

        cartVo.setCartTotalPrice(cartTotalPrice); //给购物车值对象设置总价
        cartVo.setCartProductVoList(cartProductVoList); //给购物车值对象设置购物车产品列表
        cartVo.setAllChecked(this.getAllCheckedStatus(userId)); //设置是否全选
        cartVo.setImageHost(PropertiesUtil.getProperty("http://image.lin.com/")); //设置图片主机前缀
        return cartVo;
    }

    /**
     * 判断是否所有产品被勾选
     * @param userId 用户id
     * @return 是否所有产品被勾选
     */
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0; //判断未被勾选数量是否等于0
    }

}
