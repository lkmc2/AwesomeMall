package com.mmall.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lkmc2 on 2018/2/10.
 * ftp工具类
 */

public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class); //日志打印器

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip"); //ftp ip地址
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user"); //ftp 用户名
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass"); //ftp 密码

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * 上传文件
     * @param fileList 上传的文件列表
     * @return 是否上传成功
     * @throws IOException 文件输入输出流异常
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img", fileList); //上传文件到img目录下

        logger.info("开始连接fto服务器，结束上传， 上传结果{}", result);
        return result; //返回上传是否成功
    }

    /**
     * 上传文件
     * @param remotePath 远程路径
     * @param fileList 上传的文件路径
     * @return 是否上传成功
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true; //是否上传成功

        FileInputStream fis = null; //文件输入流
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath); //ftp转换工作目录为远程路径
                ftpClient.setBufferSize(1024); //设置缓冲大小
                ftpClient.setControlEncoding("UTF-8"); //设置编码类型
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE); //设置文件类型为二进制文件
                ftpClient.enterLocalPassiveMode(); //打开本地被动模式

                for (File fileItem : fileList) { //遍历文件列表
                    fis = new FileInputStream(fileItem); //打开文件流
                    ftpClient.storeFile(fileItem.getName(), fis); //存储文件到ftp服务器
                }
            } catch (IOException e) {
                uploaded = false; //将上传状态置为失败
                logger.error("上传文件异常", e);
                e.printStackTrace();
            } finally {
                fis.close(); //关闭输入流
                ftpClient.disconnect(); //关闭ftp服务器连接
            }
        }
        return uploaded;
    }

    /**
     * 连接服务器
     * @param ip ip地址
     * @param port 端口
     * @param user 用户名
     * @param pwd 密码
     * @return 是否连接成功
     */
    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false; //是否连接成功

        ftpClient = new FTPClient(); //新建Ftp客户端
        try {
            ftpClient.connect(ip); //连接ip地址
            isSuccess = ftpClient.login(user, pwd); //用户名和密码登陆ftp服务器
        } catch (IOException e) {
            logger.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }

    private String ip; //ip地址
    private int port; //端口号
    private String user; //用户名
    private String pwd; //密码
    private FTPClient ftpClient; //ftp客户端

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
