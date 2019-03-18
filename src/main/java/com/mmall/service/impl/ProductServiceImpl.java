package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品服务实现类
 */

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper; //连接数据库的产品匹配接口（相当于Dao）

    @Autowired
    private CategoryMapper categoryMapper; //连接数据库的分类匹配接口（相当于Dao）

    private ICategoryService iCategoryService; //分类服务

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

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId); //通过id从数据库中选择产品
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        //VO对象--value object
        ProductDetailVo productDetailVo = assembleProduct(product); //根据产品生成产品值对象
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 根据产品生成产品值对象
     * @param product 产品
     * @return 产品值对象
     */
    private ProductDetailVo assembleProduct(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.lin.com/"));;
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId()); //根据id从数据库中获取该类别
        if (category == null) { //该分类为空
            productDetailVo.setParentCategoryId(0); //默认根节点
        } else {
            productDetailVo.setParentCategoryId(category.getParentId()); //设置父分类id
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); //开始进行分页
        List<Product> productList = productMapper.selectList(); //获取产品列表

        List<ProductListVo> productListVoList = Lists.newArrayList(); //新建列表
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem); //根据产品生成产品列表值对象
            productListVoList.add(productListVo); //将对象添加到列表
        }
        PageInfo pageResult = new PageInfo(productList); //创建页面信息
        pageResult.setList(productListVoList); //设置页面列表
        return ServerResponse.createBySuccess(pageResult); //返回带列表信息的响应
    }

    /**
     * 根据产品生成产品列表值对象
     * @param product 产品
     * @return 产品列表值对象
     */
    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo(); //产品列表值对象
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.lin.com/"));
        productListVo.setMainImage(product.getSubImages());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); //开始分页
        if (StringUtils.isNotBlank(productName)) { //产品名非空
            productName = "%" + productName + "%"; //给产品名前后添加百分号
        }

        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);//通过产品名或产品id选择产品
        List<ProductListVo> productListVoList = Lists.newArrayList(); //新建列表
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem); //根据产品生成产品列表值对象
            productListVoList.add(productListVo); //将对象添加到列表
        }
        PageInfo pageResult = new PageInfo(productList); //创建页面信息
        pageResult.setList(productListVoList); //设置页面列表
        return ServerResponse.createBySuccess(pageResult); //返回带列表信息的响应
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId); //通过id从数据库中选择产品
        if (product == null) { //查询到的产品为空
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) { //产品为非在线状态
            return ServerResponse.createByErrorMessage("产品已经下架或者删除");
        }

        //VO对象--value object
        ProductDetailVo productDetailVo = assembleProduct(product); //根据产品生成产品值对象
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                                int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) { //关键词为空或分类id为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<Integer>(); //分类id列表
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId); //根据id查询分类
            if (category == null && StringUtils.isBlank(keyword)) { //没有该分类，也没有该关键字
                PageHelper.startPage(pageNum, pageSize); //开始分页
                List<ProductListVo> productListVoList = Lists.newArrayList(); //新建列表
                PageInfo pageInfo = new PageInfo(productListVoList); //生成页面信息
                return ServerResponse.createBySuccess(pageInfo); //返回空结果集
            }

            //根据id查询当前节点的分类id或其子节点的分类id
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }

        if (StringUtils.isNotBlank(keyword)) { //关键词非空
            keyword = "%" + keyword + "%"; //关键词前后加百分号
        }

        PageHelper.startPage(pageNum, pageSize); //开始分页
        //排序处理
        if (StringUtils.isNotBlank(orderBy)) { //排序非空
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) { //如果该关键词在集合中
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]); //进行分页排序
            }
        }
        //根据名字或分类id获取产品列表
        List<Product> productList = productMapper.selectByNameAndCategoryId(
                StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() == 0 ? null : categoryIdList);

        List<ProductListVo> productListVoList = Lists.newArrayList(); //新建列表
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductListVo(product); //生成产品列表值对象
            productListVoList.add(productListVo); //将对象添加到列表
        }
        PageInfo pageInfo = new PageInfo(productList); //生成页面信息
        pageInfo.setList(productListVoList); //设置列表
        return ServerResponse.createBySuccess(pageInfo); //返回带页面信息的响应
    }
}
