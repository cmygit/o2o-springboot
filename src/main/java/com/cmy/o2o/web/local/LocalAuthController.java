package com.cmy.o2o.web.local;

import com.cmy.o2o.constant.ControllerConst;
import com.cmy.o2o.dto.LocalAuthExecution;
import com.cmy.o2o.dto.PersonInfoExecution;
import com.cmy.o2o.entity.LocalAuth;
import com.cmy.o2o.entity.PersonInfo;
import com.cmy.o2o.enums.LocalAuthStateEnum;
import com.cmy.o2o.service.LocalAuthService;
import com.cmy.o2o.service.PersonInfoService;
import com.cmy.o2o.util.CodeUtil;
import com.cmy.o2o.util.HttpServletRequestUtil;
import com.cmy.o2o.util.wechat.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : cmy
 * Date   : 2018-03-12 13:38.
 * desc   :
 */
@RestController
@RequestMapping(value = "/local")
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    private static final String FRONTEND = "1";

    private static final String SHOPEND = "2";

    /**
     * 绑定平台帐号
     *
     * @param request
     *
     * @return
     */
    @PostMapping(value = "/bindlocalauth")
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        boolean registerFlag = HttpServletRequestUtil.getBoolean(request, "registerFlag");
        // 是否需要验证码验证
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, ControllerConst.NEED_VERIFY);
        // 用户类型
        String userType = HttpServletRequestUtil.getString(request, ControllerConst.USER_TYPE);
        // 验证码校验
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }

        // 判断是注册还是绑定帐号，如果是注册，则先注册用户信息，再绑定平台帐号
        if (registerFlag) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setAdminFlag(1);
            if (FRONTEND.equals(userType)) {
                personInfo.setName("用户");
            } else if (SHOPEND.equals(userType)) {
                personInfo.setName("商家");
            }
            try {
                PersonInfoExecution pe = personInfoService.addPersonInfo(personInfo);
                // 获取注册后的用户信息
                PersonInfo registerUser = personInfoService.getPersonInfoById(pe.getPersonInfo().getUserId());
                // 用户信息保存到session中
                request.getSession().setAttribute(ControllerConst.USER, registerUser);
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, e.getMessage());
            }
        }
        // 获取输入的帐号
        String userName = HttpServletRequestUtil.getString(request, ControllerConst.USERNAME);
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, ControllerConst.PASSWORD);
        // 从session中获取当前用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute(ControllerConst.USER);
        // 非空判断
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            // 创建LocalAuth对象
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            // 绑定帐号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put(ControllerConst.SUCCESS, true);
            } else {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, le.getStateInfo());
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param request
     *
     * @return
     */
    @PostMapping(value = "/changelocalpwd")
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String userName = HttpServletRequestUtil.getString(request, ControllerConst.USERNAME);
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, ControllerConst.PASSWORD);
        // 获取输入新密码
        String newPassword = HttpServletRequestUtil.getString(request, ControllerConst.NEW_PASSWORD);
        // 从session中获取当前用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute(ControllerConst.USER);
        // 非空判断，要求帐号，新旧密码以及当前的用户session非空，切新旧密码不相同
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
                && !password.equals(newPassword)) {
            try {
                // 查看原先帐号，判断与输入帐号是否一致
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUserName().equals(userName)) {
                    // 不一致则直接返回
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, "输入的帐号费本次登录的帐号");
                    return modelMap;
                }
                // 修改平台帐号的用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put(ControllerConst.SUCCESS, true);
                } else {
                    modelMap.put(ControllerConst.SUCCESS, false);
                    modelMap.put(ControllerConst.ERR_MSG, le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, "请输入密码");
                return modelMap;
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "请输入密码");
        }
        return modelMap;
    }

    /**
     * 登录校验
     *
     * @param request
     *
     * @return
     */
    @PostMapping(value = "/logincheck")
    private Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, ControllerConst.NEED_VERIFY);
        // 验证码校验
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String userName = HttpServletRequestUtil.getString(request, ControllerConst.USERNAME);
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, ControllerConst.PASSWORD);
        // 非空校验
        if (userName != null && password != null) {
            // 传入帐号和密码去获取平台帐号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                // 若能取到帐号信息则登录成功
                modelMap.put(ControllerConst.SUCCESS, true);
                // 同时在session里设置用户信息
                request.getSession().setAttribute(ControllerConst.USER, localAuth.getPersonInfo());;
            } else {
                modelMap.put(ControllerConst.SUCCESS, false);
                modelMap.put(ControllerConst.ERR_MSG, "用户名或密码错误");
            }
        } else {
            modelMap.put(ControllerConst.SUCCESS, false);
            modelMap.put(ControllerConst.ERR_MSG, "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 当用户点击登出按钮时注销session
     *
     * @param request
     *
     * @return
     */
    @PostMapping(value = "/logout")
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 将用户session置为空
        request.getSession().setAttribute(ControllerConst.USER, null);
        modelMap.put(ControllerConst.SUCCESS, true);
        return modelMap;
    }

}
