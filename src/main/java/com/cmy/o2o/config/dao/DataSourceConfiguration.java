package com.cmy.o2o.config.dao;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.beans.PropertyVetoException;

/**
 * Author : cmy
 * Date   : 2018-03-13 11:18.
 * desc   : 配置DataSource到IOC容器里面
 */
//@Configuration
//// 配置mybatis mapper的扫描路径
//@MapperScan("com.cmy.o2o.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

//    @Bean(name = "dataSource")
//    public DataSource createDataSource1() throws PropertyVetoException {
//        //生成DataSource实例
//        DataSource dataSource = new DataSource();
//        // 数据库驱动
//        dataSource.setDriverClassName(jdbcDriver);
//        // 数据库连接url
//        dataSource.setUrl(jdbcUrl);
//        // 用户名(需解密)
//        dataSource.setUsername(jdbcUsername);
//        // 密码(需解密)
//        dataSource.setPassword(jdbcPassword);
//        return dataSource;
//    }
}
