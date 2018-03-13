package com.cmy.o2o.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Author : cmy
 * Date   : 2018-03-13 14:00.
 * desc   : 事务管理配置
 */
@Configuration
// 开启事务支持
@EnableTransactionManagement
public class TransactionManagerConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    /**
     * 事务管理，需要返回PlatformTransactionManager的实现
     *
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
