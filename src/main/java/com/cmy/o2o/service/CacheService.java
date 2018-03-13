package com.cmy.o2o.service;

/**
 * Author : cmy
 * Date   : 2018-03-11 17:35.
 * desc   :
 */
public interface CacheService {

    /**
     * 依据key前缀删除匹配该模式下的所有key-value
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
