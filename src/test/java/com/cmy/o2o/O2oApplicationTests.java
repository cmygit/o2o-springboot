package com.cmy.o2o;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class O2oApplicationTests {

    @Autowired
    /* 配置文件密文加密器 */
    private StringEncryptor stringEncryptor;

    @Test
    public void contextLoads() {

    }
}
