package com.cmy.o2o;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 配置mybatis mapper的扫描路径
@MapperScan("com.cmy.o2o.dao")
public class O2oApplication {

    @Autowired
    /* 配置文件密文加密器 */
    private StringEncryptor stringEncryptor;

    public static void main(String[] args) {
        SpringApplication.run(O2oApplication.class, args);
    }
}
