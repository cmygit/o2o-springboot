package com.cmy.o2o.cache;

import lombok.Data;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Author : cmy
 * Date   : 2018-03-11 13:58.
 * desc   : Redis连接池，并做相关配置
 */
@Data
public class JedisPoolWriper {

    /* Redis连接池对象 */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host, final int port) {
        try {
            this.jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
