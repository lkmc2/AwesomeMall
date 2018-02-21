package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * Created by lkmc2 on 2018/2/10.
 * 属性工具类
 */

public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class); //日志记录器

    private static Properties props; //属性

    static {
        String fileName = "mmall.properties"; //将读取的属性文件名
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    /**
     * 获取属性值
     * @param key 属性名
     * @return 属性值
     */
    public static String getProperty(String key) {
        String value = props.getProperty(key.trim()); //获取配置文件中的属性
        if (StringUtils.isBlank(value)) { //属性为空
            return null;
        }
        return value.trim(); //返回属性值
    }

    /**
     * 获取属性值属性值为空时返回指定的默认值
     * @param key 属性名
     * @param defaultValue 指定的默认值
     * @return 属性值
     */
    public static String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim()); //获取配置文件中的属性
        if (StringUtils.isBlank(value)) { //属性为空
            return defaultValue;
        }
        return value.trim(); //返回属性值
    }
}
