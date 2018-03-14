package com.cmy.o2o.service;

import com.cmy.o2o.dto.LocalAuthExecution;
import com.cmy.o2o.entity.LocalAuth;
import com.cmy.o2o.exception.LocalAuthOperaException;

/**
 * Author : cmy
 * Date   : 2018-03-12 12:14.
 * desc   :
 */
public interface LocalAuthService {

    /**
     * 通过帐号和密码获取平台帐号信息
     *
     * @param userName
     * @param password
     *
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 通过userId获取平台帐号信息
     *
     * @param userId
     *
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 绑定微信，生成平台专属帐号
     *
     * @param localAuth
     * @return
     *
     * @throws RuntimeException
     */
    LocalAuthExecution insertLoaclAuth(LocalAuth localAuth) throws LocalAuthOperaException;

    /**
     *
     *
     * @param localAuth
     *
     * @return
     *
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     * 修改平台帐号的登录密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     *
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
