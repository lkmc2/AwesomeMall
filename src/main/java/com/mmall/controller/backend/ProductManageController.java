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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lkmc2 on 2018/2/10.
 * 产品管理控制器（后台）
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService; //用户服务接口

    @Autowired
    private IProductService iProductService; //产品服务接口

    @Autowired
    private IFileService iFileService; //ftp服务接口

    /**
     * 保存产品（已存在则更新，未存在则插入）
     *
     * @param session 浏览器session
     * @param product 产品
     * @return 是否保存成功
     */
    @RequestMapping("save.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse productSave(HttpSession session, Product product) {
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

    /**
     * 设置商品销售状态
     *
     * @param session   浏览器session
     * @param productId 产品id
     * @param status    销售状态
     * @return 设置状态是否成功
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 根据id获取产品详情
     *
     * @param session   浏览器详情
     * @param productId 产品id
     * @return 产品详情
     */
    @RequestMapping("detail.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 获取产品列表
     *
     * @param session  浏览器详情
     * @param pageNum  页号
     * @param pageSize 展示页面产品数量
     * @return 产品列表
     */
    @RequestMapping("list.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 根据查询条件搜索产品
     *
     * @param session     浏览器session
     * @param productName 产品名
     * @param productId   产品id
     * @param pageNum     当前页数
     * @param pageSize    展示产品条数
     * @return 搜索到的产品
     */
    @RequestMapping("search.do")
    @ResponseBody //使返回值自动使用json序列化
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

    /**
     * 上传文件
     *
     * @param session 浏览器session
     * @param file    需要上传的文件
     * @param request http请求
     * @return 带上传成功信息的响应
     */
    @RequestMapping("upload.do")
    @ResponseBody //使返回值自动使用json序列化
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER); //从session中获取用户数据
        if (user == null) { //用户为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) { //用户是管理员
            String path = request.getSession().getServletContext().getRealPath("upload"); //获取上传到的文件夹路径
            String targetFileName = iFileService.upload(file, path); //上传文件到指定路径
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName; //上传文件成功后生成url

            Map fileMap = Maps.newHashMap(); //生成新的HashMap
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else { //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 富文本图片上传
     *
     * @param session 浏览器会话
     * @param file    文件
     * @param request 请求
     * @param response 响应
     * @return 带上传数据的响应
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody //使返回值自动使用json序列化
    public Map richtextImgUpload(HttpSession session,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Map resultMap = Maps.newHashMap(); //新建HashMap
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
