package com.cmy.o2o.config.dao;

import com.cmy.o2o.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Author : cmy
 * Date   : 2018-03-13 11:18.
 * desc   : 配置DataSource到IOC容器里面
 */
@Configuration
// 配置mybatis mapper的扫描路径
@MapperScan("com.cmy.o2o.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(name = "ComboPooledDataSource")
    public ComboPooledDataSource createDataSource1() throws PropertyVetoException {
        //生成DataSource实例
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 数据库驱动
        dataSource.setDriverClass(jdbcDriver);
        // 数据库连接url
        dataSource.setJdbcUrl(jdbcUrl);
        // 用户名(需解密)
        dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
        // 密码(需解密)
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        // 配置擦c3p0连接池的私有属性
        // 连接池最大线程数
        dataSource.setMaxPoolSize(30);
        // 连接池最小线程数
        dataSource.setMinPoolSize(10);
        // 关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        // 连接超时时间
        dataSource.setCheckoutTimeout(8000);
        // 连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}
