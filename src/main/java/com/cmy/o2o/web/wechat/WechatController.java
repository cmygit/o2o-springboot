package com.cmy.o2o.web.wechat;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.util.wechat.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author : cmy
 * Date   : 2018-03-09 23:48.
 * desc   : 处理来自微信的token验证
 */
@Controller
// 该url配置在微信开发应用配置规则中
@RequestMapping("/wechat")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @GetMapping()
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("wechat get...");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter(ControllerConst.SIGNATURE);
        // 时间戳
        String timestamp = request.getParameter(ControllerConst.TIME_STAMP);
        // 随机数
        String nonce = request.getParameter(ControllerConst.NONCE);
        // 随机字符串
        String echostr = request.getParameter(ControllerConst.ECHO_STR);

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                logger.debug("wechat get success...");
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
