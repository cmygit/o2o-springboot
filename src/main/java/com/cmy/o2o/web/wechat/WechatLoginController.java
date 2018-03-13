package com.cmy.o2o.web.wechat;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.UserAccessToken;
import com.cmy.o2o.dto.WechatAuthExecution;
import com.cmy.o2o.dto.WechatUser;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.entity.WechatAuth;
import com.cmy.o2o.enums.WechatAuthStateEnum;
import com.cmy.o2o.service.PersonInfoService;
import com.cmy.o2o.service.WechatAuthService;
import com.cmy.o2o.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author : cmy
 * Date   : 2018-03-10 13:13.
 * desc   : 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&redirect_uri=http://配置的二级域名/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect *
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 */
@Controller
@RequestMapping("/wechatlogin")
public class WechatLoginController {

    private static Logger logger = LoggerFactory.getLogger(WechatLoginController.class);

    private static final String FRONTEND = "1";

    private static final String SHOPEND = "1";

    @Autowired
    private WechatAuthService wechatAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping(value = "/logincheck")
    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("wechat login get...");
        // 获取微信公众号传输过来的code，通过code可获取access_token,进而获取用户信息
        String code = request.getParameter(ControllerConst.CODE);
        // state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");
        logger.debug("wechat login code:" + code);

        String openId = null;
        WechatUser user = null;
        WechatAuth wechatAuth = null;

        if (code != null) {
            UserAccessToken token = null;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                logger.debug("wechat login token:" + token.toString());
                // 通过token获取accesstoken
                String accesToken = token.getAccessToken();
                logger.debug("wechat login accesToken:" + accesToken.toString());
                // 通过token获取openId
                openId = token.getOpenId();
                logger.debug("wechat login openId:" + openId.toString());
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accesToken, openId);
                logger.debug("wechat login user:" + user.toString());
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
                request.getSession().setAttribute(ControllerConst.openId, openId);
            } catch (Exception e) {
                logger.error("error in getUserAccessToken or getAccessToken or getOpenId" + e.toString());
                e.printStackTrace();
                return null;
            }
        }

        if (wechatAuth == null) {
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            if (FRONTEND.equals(roleType)) {
                // 顾客用户
                personInfo.setAdminFlag(1);
            } else {
                // 商家用户
                personInfo.setAdminFlag(2);
            }
            wechatAuth = new WechatAuth();
            wechatAuth.setPersonInfo(personInfo);
            wechatAuth.setOpenId(openId);
            WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
            if (wae.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                return null;
            } else {
                personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
                request.getSession().setAttribute(ControllerConst.USER, personInfo);
            }
        }

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        if (FRONTEND.equals(roleType)) {
            // 如果用户点击的是前端展示系统按钮则进入前端展示系统
            return "frontend/index";
        } else {
            // 如果用户点击的是店铺展示系统按钮则进入店铺展示系统
            return "shopadmin/index";
        }
    }
}
