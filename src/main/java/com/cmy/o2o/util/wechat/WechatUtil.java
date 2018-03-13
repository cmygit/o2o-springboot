package com.cmy.o2o.util.wechat;

import com.cmy.o2o.dto.UserAccessToken;
import com.cmy.o2o.dto.WechatUser;
import com.cmy.o2o.entity.PersonInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Author : cmy
 * Date   : 2018-03-10 13:37.
 * desc   : 微信工具类
 */
public class WechatUtil {

    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    public static PersonInfo getPersonInfoFromRequest(WechatUser user) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(user.getNickName());
        personInfo.setGender(String.valueOf(user.getSex()));
        personInfo.setProfileImg(user.getHeadImgUrl());
        personInfo.setEnableStatus(1);
        return personInfo;
    }

    /**
     * 获取UserAccessToken实体类
     *
     * @param code
     *
     * @return
     */
    public static UserAccessToken getUserAccessToken(String code) {
        // 微信开放平台的相关配置
        // appId
        String appId = "wx6a1e22a8e624f7db";
        logger.debug("addId:" + appId);
        // appsecret
        String appsecret = "6d54da2e8c2942c283d8d40815b14d43";
        logger.debug("appsecret:" + appsecret);
        // 根据传入的code，拼接出访问微信定义好的接口的url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
                + "&secret=" + appsecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        // 向相应的url发送请求获取token json字符串
        String tokenStr = httpsRequest(url, "GET", null);
        logger.debug("userAccessToken:" + tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (token == null) {
            logger.error("获取用户assessToken失败.");
            return null;
        }
        return token;
    }

    /**
     * 获取Wechatuser实体类
     *
     * @param accessToken
     * @param openId
     *
     * @return
     */
    public static WechatUser getUserInfo(String accessToken, String openId) {
        // asscessToken以及openId，拼接出访问微信定义好的接口的url
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken
                + "&openid=" + openId
                + "&lang=zh_CN";
        // 访问该url获取用户信息json字符串
        String userStr = httpsRequest(url, "GET", null);
        logger.debug("user info:" + userStr);
        WechatUser user = new WechatUser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将就送字符串转换成相应对象
            user = objectMapper.readValue(userStr, WechatUser.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user == null) {
            return null;
        }
        return user;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     *
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            logger.debug("https buffer:" + buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return buffer.toString();
    }
}
