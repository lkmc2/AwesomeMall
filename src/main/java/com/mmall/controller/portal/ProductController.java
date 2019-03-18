package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品控制器
 */

@Api(value = "产品管理的接口", tags = {"产品管理的Controller"})
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService; //产品服务接口


    @ApiOperation(value = "获取产品详情", notes = "获取产品详情")
    @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "int", paramType = "query")
    @PostMapping("detail")
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId); //根据id查询产品细节
    }

    @ApiOperation(value = "根据查询条件获取产品列表", notes = "根据查询条件获取产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询关键词", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面展示产品条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排列顺序（升序、降序）", dataType = "String", paramType = "query")
    })
    @PostMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        //根据关键词或者分类查询产品
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
