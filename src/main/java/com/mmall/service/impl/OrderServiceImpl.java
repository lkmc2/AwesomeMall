package com.mmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.FTPUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderProductVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lkmc2 on 2018/2/20.
 * 订单服务实现类
 */

@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class); //日志记录器
    @Autowired
    private OrderMapper orderMapper; //连接数据库的订单匹配器，相当于Dao
    @Autowired
    private OrderItemMapper orderItemMapper; //连接数据库的订单子项匹配器，相当于Dao
    @Autowired
    private PayInfoMapper payInfoMapper; //连接数据库的支付信息匹配器，相当于Dao
    @Autowired
    private CartMapper cartMapper; //连接数据库的购物车匹配器，相当于Dao
    @Autowired
    private ProductMapper productMapper; //连接数据库的产品匹配器，相当于Dao
    @Autowired
    private ShippingMapper shippingMapper; //连接数据库的收货地址匹配器，相当于Dao


    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        //从购物车获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId); //根据用户id查询被勾选的购物车

        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList); //获取购物车订单子项
        if (!serverResponse.isSuccess()) { //响应不成功
            return serverResponse;
        }

        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData(); //获取响应中的订单子项数据
        if (CollectionUtils.isEmpty(orderItemList)) { //订单子项列表为空
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        BigDecimal payment = this.getOrderTotalPrice(orderItemList); //计算订单子项列表中所有商品的总价

        Order order = this.assembleOrder(userId, shippingId, payment); //生成订单
        if (order == null) { //生成订单失败
            return ServerResponse.createByErrorMessage("生成订单错误");
        }

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo()); //批量设置订单号
        }

        //mybatis 批量插入订单子项列表
        orderItemMapper.batchInsert(orderItemList);

        //生成成功，需要减少产品库存
        this.reduceProductStock(orderItemList);

        this.cleanCart(cartList); //清空购物车

        //返回数据给前端
        OrderVo orderVo = assembleOrderVo(order, orderItemList); //生成订单值对象
        return ServerResponse.createBySuccess(orderVo); //返回带订单值对象的响应
    }

    /**
     * 生成订单值对象
     *
     * @param order         订单
     * @param orderItemList 订单子项列表
     * @return 订单值对象
     */
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo(); //新建订单值对象

        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(assembleShippingVo(shipping)); //设置收货地址值对象
        }

        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        List<OrderItemVo> orderItemVoList = Lists.newArrayList(); //新建列表
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem); //生成订单子项值对象
            orderItemVoList.add(orderItemVo);
        }

        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * 生成订单子项值对象
     *
     * @param orderItem 订单子项
     * @return 订单子项值对象
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo(); //新建订单子项值对象
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));

        return orderItemVo;
    }

    /**
     * 生成收货地址值对象
     *
     * @param shipping 收货地址
     * @return 收货地址值对象
     */
    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo(); //新建收货地址值对象
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shipping.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        return shippingVo;
    }

    /**
     * 清空购物车
     *
     * @param cartList 购物车列表
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId()); //根据购物车id删除购物车
        }
    }

    /**
     * 减少产品库存
     *
     * @param orderItemList 订单子项列表
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId()); //根据产品id选择产品
            product.setStock(product.getStock() - orderItem.getQuantity()); //设置新的库存（减去订单子项中该产品的数量）
            productMapper.updateByPrimaryKeySelective(product); //选择性更新产品
        }
    }

    /**
     * 生成订单
     *
     * @param userId     用户id
     * @param shippingId 发货地址id
     * @param payment    付款金额
     * @return 生成的订单
     */
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order(); //新建订单
        long orderNo = this.generateOrderNo(); //生成订单号
        order.setOrderNo(orderNo); //设置订单号
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode()); //设置状态为未付款
        order.setPostage(0); //设置运费
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode()); //设置在线付款

        order.setUserId(userId); //设置用户id
        order.setShippingId(shippingId); //设置送货地址id
        //发货时间
        //付款时间

        int rowCount = orderMapper.insert(order); //将订单插入数据库
        if (rowCount > 0) { //插入成功
            return order; //返回订单
        }
        return null;
    }

    /**
     * 生成订单号
     *
     * @return 订单号
     */
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis(); //获取当前时间
        return currentTime + new Random().nextInt(100);
    }


    /**
     * 计算订单子项列表中所有商品的总价
     *
     * @param orderItemList 订单子项列表
     * @return 列表中所有商品的总价
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0"); //统计价格
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment; //返回总价
    }

    /**
     * 获取购物车订单子项
     *
     * @param userId   用户id
     * @param cartList 购物车列表
     * @return 带购物车子项列表的响应
     */
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        if (CollectionUtils.isEmpty(cartList)) { //列表为空
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        List<OrderItem> orderItemList = Lists.newArrayList(); //新建列表

        //校验购物车数据，包括产品的状态和数量
        for (Cart cartItem : cartList) {
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId()); //根据产品id从数据库获取产品
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) { //产品非在售状态
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "不是在线售卖状态");
            }

            //校验库存
            if (cartItem.getQuantity() > product.getStock()) { //购物车中的数量大于数据库中的数量
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
            }

            OrderItem orderItem = new OrderItem(); //新建订单子项
            orderItem.setUserId(userId); //设置用户id
            orderItem.setProductId(product.getId()); //设置产品id
            orderItem.setProductName(product.getName()); //设置产品名
            orderItem.setProductImage(product.getMainImage()); //设置产品图
            orderItem.setCurrentUnitPrice(product.getPrice()); //设置当前购买价格
            orderItem.setQuantity(cartItem.getQuantity()); //设置数量
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                    cartItem.getQuantity())); //设置总价
            orderItemList.add(orderItem); //将订单子项添加到列表中
        }
        return ServerResponse.createBySuccess(orderItemList); //返回带订单子项列表的响应
    }

    @Override
    public ServerResponse<String> cancel(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo); //根据用户id和订单号选择订单
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) { //订单状态不等于未付款
            return ServerResponse.createByErrorMessage("已付款，无法取消订单");
        }
        Order updateOrder = new Order(); //新建订单对象
        updateOrder.setId(order.getId()); //设置订单id
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode()); //设置订单状态为已取消

        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder); //选择性更新订单
        if (rowCount > 0) { //更新成功
            return ServerResponse.createBySuccess(); //返回成功的响应
        }
        return ServerResponse.createByError(); //返回失败的响应
    }

    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo(); //新建订单产品值对象

        //从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId); //根据用户id获取被选择的购物车
        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList); //获取购物车产品子项
        if (!serverResponse.isSuccess()) { //响应不成功
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData(); //获取响应中的数据

        List<OrderItemVo> orderItemVoList = Lists.newArrayList(); //新建订单子项值对象列表

        BigDecimal payment = new BigDecimal("0"); //用于计算总价
        for (OrderItem orderItem : orderItemList) { //计算所有产品总价
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem)); //将订单子项变成值对象添加到列表
        }
        orderProductVo.setProductTotalPrice(payment); //设置产品总价
        orderProductVo.setOrderItemVoList(orderItemVoList); //设置订单子项值对象列表
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix")); //设置图片地址前缀

        return ServerResponse.createBySuccess(orderProductVo); //带订单产品值对象的响应
    }

    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo); //根据用户id和订单号查询订单
        if (order == null) { //查询到的订单非空
            return ServerResponse.createByErrorMessage("没有找到该订单");
        }

        //根据订单号和用户id查询订单子项
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        OrderVo orderVo = assembleOrderVo(order, orderItemList); //生成订单值对象
        return ServerResponse.createBySuccess(orderVo); //返回带订单值对象的响应
    }

    @Override
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); //开始分页
        List<Order> orderList = orderMapper.selectByUserId(userId); //根据用户id查询订单
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList, userId); //生成订单值对象列表
        PageInfo pageResult = new PageInfo(orderList); //新建分页信息
        pageResult.setList(orderVoList); //设置订单值对象列表
        return ServerResponse.createBySuccess(pageResult); //返回带分页信息的响应
    }

    /**
     * 生成订单值对象列表
     * @param orderList 订单列表
     * @param userId 用户id
     * @return 订单值对象列表
     */
    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList(); //新建值对象列表
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList(); //新建产品子项列表
            if (userId != null) { //用户查询
                //根据订单号和用户id获取订单子项
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            } else { //管理员查询
                //todo 管理员查询的时候 不需要传userId
            }
            OrderVo orderVo = assembleOrderVo(order, orderItemList); //生成订单值对象
            orderVoList.add(orderVo); //将订单值对象添加到列表
        }
        return orderVoList;
    }


    @Override
    public ServerResponse pay(Long orderNo, Integer userId, String path) {
        Map<String, String> resultMap = Maps.newHashMap(); //新建map
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo); //根据用户id和订单号查询订单
        if (order == null) { //订单为空
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo())); //填充数据到map

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "mmall扫码支付，订单号：" + outTradeNo;

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "订单" + outTradeNo + "购买商品共" + totalAmount + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        //根据订单号和用户id获取订单子项
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);

        for (OrderItem orderItem : orderItemList) { //遍历订单子项列表
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods1 = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), 100d).longValue(),
                    orderItem.getQuantity());
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods1);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        /* 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
           Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /* 使用Configs提供的默认参数
           AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path); //打开文件
                if (!folder.exists()) { //文件不存在
                    folder.setWritable(true); //设置可写
                    folder.mkdirs(); //创建文件
                }

                // 需要修改为运行机器上的路径（生成二维码路径）
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath); //生成二维码到指定路径

                File targetFile = new File(path, qrFileName); //目标文件
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile)); //上传到ftp服务器
                } catch (IOException e) {
                    logger.error("上传二维码异常", e);
                }
                logger.info("qrPath:" + qrPath);

                //二维码网址
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                resultMap.put("qrUrl", qrUrl);
                return ServerResponse.createBySuccess(resultMap); //带请求参数的响应

            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        Long orderNo = Long.valueOf(params.get("out_trade_no")); //获取订单号
        String tradeNo = params.get("trade_no"); //支付宝交易号
        String tradeState = params.get("trade_status"); //交易状态

        Order order = orderMapper.selectByOrderNo(orderNo); //根据订单号获取订单
        if (order == null) { //订单为空
            return ServerResponse.createByErrorMessage("非商城订单，回调忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) { //订单状态大于等于已付款
            return ServerResponse.createBySuccessMessage("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeState)) { //交易状态为付款成功
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment"))); //设置交易付款时间
            order.setStatus(Const.OrderStatusEnum.PAID.getCode()); //设置订单状态为已付款
            orderMapper.updateByPrimaryKeySelective(order); //选择性更新订单数据
        }

        PayInfo payInfo = new PayInfo(); //付款信息
        payInfo.setUserId(order.getUserId()); //设置用户id
        payInfo.setOrderNo(order.getOrderNo()); //设置订单号
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode()); //设置支付平台为支付宝
        payInfo.setPlatformNumber(tradeNo); //设置支付宝交易号
        payInfo.setPlatformStatus(tradeState); //设置支付状态

        payInfoMapper.insert(payInfo); //将支付信息插入数据库

        return ServerResponse.createBySuccess(); //返回成功的响应
    }

    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo); //根据用户id和订单号查询订单
        if (order == null) { //订单为空
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) { //支付状态大于等于已支付
            return ServerResponse.createBySuccess(); //返回成功的响应
        }
        return ServerResponse.createByError(); //返回失败的响应
    }
}
