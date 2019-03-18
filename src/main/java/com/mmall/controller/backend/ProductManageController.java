package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品管理控制器（后台）
 */
@Api(value = "后台产品管理的接口", tags = {"后台产品管理的Controller"})
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService; //用户服务接口

    @Autowired
    private IProductService iProductService; //产品服务接口

    @Autowired
    private IFileService iFileService; //ftp服务接口


    @ApiOperation(value = "保存产品", notes = "已存在则更新，未存在则插入")
    @PostMapping("save")
    public ServerResponse productSave(HttpSession session, @RequestBody Product product) {
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

    @ApiOperation(value = "设置商品销售状态", notes = "设置商品销售状态的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", example = "26", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "销售状态", example = "1", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("set_sale_status")
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

    @ApiOperation(value = "根据id获取产品详情", notes = "根据id获取产品详情的接口")
    @ApiImplicitParam(name = "productId", value = "产品id", example = "26", required = true, dataType = "int", paramType = "query")
    @PostMapping("detail")
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

    @ApiOperation(value = "获取产品列表", notes = "获取产品列表的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", dataType = "int", example = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "展示页面产品数量", dataType = "int", example = "10", paramType = "query")
    })
    @PostMapping("list")
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iProductService.getProductList(pageNum, pageSize); //获取产品列表
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "根据查询条件搜索产品", notes = "根据查询条件搜索产品的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "产品名", example = "iPhone", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "productId", value = "产品id", example = "26", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", example = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "展示产品条数", example = "10", dataType = "int", paramType = "query")
    })
    @PostMapping("search")
    public ServerResponse productSearch(HttpSession session,
                                        String productName,
                                        Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            return iProductService.searchProduct(productName, productId, pageNum, pageSize); //根据产品名或者产品id搜索产品
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "上传文件", notes = "上传文件的接口")
    @PostMapping("upload")
    public ServerResponse upload(HttpSession session,
                                 @ApiParam(value = "上传的文件", required = true)
                                 @RequestParam(value = "upload_file")
                                 MultipartFile file,
                                 HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传到的文件夹路径
            String targetFileName = iFileService.upload(file, path); //上传文件到指定路径
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName; //上传文件成功后生成url

            Map<String, String> fileMap = Maps.newHashMap(); //生成新的HashMap
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation(value = "富文本图片上传", notes = "富文本图片上传的接口")
    @PostMapping("richtext_img_upload")
    public Map<String, Object> richtextImgUpload(HttpSession session,
                                 @ApiParam(value = "上传的文件", required = true)
                                 @RequestParam(value = "upload_file")
                                 MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Map<String, Object> resultMap = Maps.newHashMap(); //新建HashMap
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }

        // 富文本中对返回值有自己的要求，需要按simditor的要求进行返回
        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传到的文件夹路径
            String targetFileName = iFileService.upload(file, path); //上传文件到指定路径

            if (StringUtils.isBlank(targetFileName)) { //目标文件名为空
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName; //上传文件成功后生成url}
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name"); //添加响应头
            return resultMap;
        } else { //非管理员
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }

}
