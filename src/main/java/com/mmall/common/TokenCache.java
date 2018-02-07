package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Token缓存
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class); //日志记录

    public static final String TOKEN_PREFIX = "token_"; //Token key的前缀

    //本地Token缓存，Guava
    private static LoadingCache<String, String> localCache
            = CacheBuilder.newBuilder()
            .initialCapacity(1000) //初始化大小
            .maximumSize(10000) //最大存储，超过这个值将调用LRU算法进行移除
            .expireAfterAccess(12, TimeUnit.HOURS) //Token有效时间
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    //默认的数据加载实现，当调get方法时，如果没有该key，就调用这个方法进行加载
                    return "null"; //返回字符串防止null调equal时报空指针异常
                }
            });

    //将值设置到本地缓存
    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    //从本地缓存中获取值
    public static String getKey(String key) {
        String value = null;

        try {
            value = localCache.get(key); //从本地缓存中获取值
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("LocalCache get error", e);
        }
        return null;
    }
}
