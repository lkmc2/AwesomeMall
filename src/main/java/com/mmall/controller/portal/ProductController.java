package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品控制器
 */

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService; //产品服务接口

    /**
     * 获取产品详情
     * @param productId 产品id
     * @return 带产品详情的响应
     */
    @RequestMapping("detail.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId); //根据id查询产品细节
    }

    /**
     * 根据查询条件获取产品列表
     * @param keyword 查询关键词
     * @param categoryId 分类id
     * @param pageNum 当前页号
     * @param pageSize 页面展示产品条数
     * @param orderBy 排列顺序（升序、降序）
     * @return 带页面信息的响应
     */
    @RequestMapping("list.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        //根据关键词或者分类查询产品
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
