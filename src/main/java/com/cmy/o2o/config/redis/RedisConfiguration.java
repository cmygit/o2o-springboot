package com.cmy.o2o.config.redis;

import com.cmy.o2o.cache.JedisPoolWriper;
import com.cmy.o2o.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Author : cmy
 * Date   : 2018-03-13 14:12.
 * desc   : redis配置
 */
@Configuration
public class RedisConfiguration {

    @Value("${redis.hostname}")
    private String hostname;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.pool.maxActive}")
    private int maxTotal;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.maxWait}")
    private long maxWaitMills;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    private JedisPoolWriper jedisPoolWriper;

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 创建redis连接池的设置
     *
     * @return
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 控制一个pool可分配多少个实例
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 连接池中最多可空闲maxIdle个连接
        // 连接池中最多可以空闲maxIdle个连接，设置20，
        // 表示即使没有数据库连接时依然可以保持20个空闲连接
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 最大等待时间：当没有可用连接时，连接池等待连接被归还的最大时间（一毫秒计数），
        // 超过时间则跑出异常
        jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
        // 在获取连接的时候检查有效性
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    /**
     * 创建redis连接池，并作相关的配置。
     *
     * @return
     */
    @Bean(name = "jedisPoolWriper")
    public JedisPoolWriper createJedisPoolWriper(){
        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
        return jedisPoolWriper;
    }

    /**
     * 创建redis工具类
     *
     * @return
     */
    @Bean(name = "jedisUtil")
    public JedisUtil createJedisUtil() {
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisPoolWriper);
        return jedisUtil;
    }

    /**
     * redis keys操作
     *
     * @return
     */
    @Bean(name = "jedisKeys")
    public JedisUtil.Keys createJedisUtilKeys() {
        JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
        return jedisKeys;
    }

    /**
     * redis strings操作
     *
     * @return
     */
    @Bean(name = "jedisStrings")
    public JedisUtil.Strings createJedisUtilStrings() {
        JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
        return jedisStrings;
    }
}
