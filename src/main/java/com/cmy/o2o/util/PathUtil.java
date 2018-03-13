package com.cmy.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Author : cmy
 * Date   : 2018-03-01 11:55.
 * desc   :
 */
@Configuration
public class PathUtil {

    /* 根据不同的系统获取斜杠或反斜杠，用于文件路径替换 */
    private static String separator = System.getProperty("file.separator");

    private static String winPath;

    private static String linuxPath;

    private static String shopPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String assertBasePath = "";
        if (os.toLowerCase().startsWith("win")) {
            assertBasePath = winPath;
        } else {
            // 服务器资源文件目录
            assertBasePath = linuxPath;
        }
        assertBasePath.replace("/", separator);
        return assertBasePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = shopPath + shopId + separator;
        return imagePath.replace("/", separator);
    }
}
