package com.cmy.o2o.service.impl;

import com.cmy.o2o.dao.LocalAuthDao;
import com.cmy.o2o.dto.LocalAuthExecution;
import com.cmy.o2o.entity.LocalAuth;
import com.cmy.o2o.enums.LocalAuthStateEnum;
import com.cmy.o2o.exception.LocalAuthOperaException;
import com.cmy.o2o.service.LocalAuthService;
import com.cmy.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Author : cmy
 * Date   : 2018-03-12 12:20.
 * desc   :
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution insertLoaclAuth(LocalAuth localAuth) throws LocalAuthOperaException {
        return null;
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {
        // 空值判断，传入的localAuth帐号密码，用户信息，尤其是userId不能为空，否则直接返回错误
        if (localAuth == null || localAuth.getUserName() == null || localAuth.getPassword() == null
                || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        // 查询此用户是否已判定过平台帐号
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (tempAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            // 如果没有绑定过平台帐号，则创建一个平台帐号与该用户绑定
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            // 对密码进行MD5加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int efftedNum = localAuthDao.insertLocalAuth(localAuth);
            // 判断是否创建成功
            if (efftedNum <= 0) {
                throw new LocalAuthOperaException("帐号绑定失败");
            }
            return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
        } catch (Exception e) {
            throw new LocalAuthOperaException("insertLocalAuth error:" + e.toString());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
        // 非空判断，判断传入的用户Id，帐号，新旧密码是否为空，新旧密码是否相同
        if (userId != null && userName != null && password != null && newPassword != null
                && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD%加密
                int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
                // 判断更新是否成功
                if (effectedNum <= 0) {
                    throw new LocalAuthOperaException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperaException("updateLocalAuth error：" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
