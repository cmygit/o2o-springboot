package com.cmy.o2o.service.impl;

import com.cmy.o2o.cache.JedisUtil;
import com.cmy.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Author : cmy
 * Date   : 2018-03-11 17:36.
 * desc   :
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        // 取出匹配对应key前缀
        Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
        // 遍历删除对应的key-value
        for (String key : keySet) {
            jedisKeys.del(key);
        }
    }
}
