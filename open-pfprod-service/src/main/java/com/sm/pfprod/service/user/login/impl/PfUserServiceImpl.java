package com.sm.pfprod.service.user.login.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.dal.user.login.PfUserDao;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.dto.user.login.LoginDto;
import com.sm.pfprod.model.dto.user.login.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.vo.user.PfUsersVo;
import com.sm.pfprod.service.common.util.AuthConstant;
import com.sm.pfprod.service.user.login.PfUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * 注册、登陆
 */
@Service("pfLoginService")
public class PfUserServiceImpl implements PfUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfLoginHelper.class);

    @Resource
    private PfUserDao pfUserDao;

    @Value("#{configProperties['private_key']}")
    private String              privateKey;             // 私钥
    @Value("#{configProperties['algorithmName']}")
    private String              algorithmName;          // 加密算法
    @Value("#{configProperties['hashIterations']}")
    private int                 hashIterations;         // 加密次数

    /**
     * 获取用户列表
     * @param dto
     * @return
     */
    @Override
    public PageInfo<PfUsersVo> listUsers(PfUserDto dto) {
        /* 设置分页默认值 */
        if (null == dto.getPageNum() || dto.getPageNum() < 1) {
            dto.setPageNum(1);
        }
        if (null == dto.getPageSize() || dto.getPageSize() < 1) {
            dto.setPageSize(15);
        }
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize()); // 设置分页查询
        List<PfUsersVo> list = pfUserDao.listUsers();
        return new PageInfo<PfUsersVo>(list);
    }

    /**
     * 保存用户信息
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean saveUser(RegisterDto dto) {
        try {
            LoggerUtil.info(LOGGER, "【PfLoginServiceImpl-saveUser-params】dto={0}", JSON.toJSONString(dto));
            UserInfo user = new UserInfo();
            BeanUtils.copyProperties(dto, user);
            /* 密码处理 */
            String plaintext = PfLoginHelper.getPlaintext(privateKey, dto.getPassword());
            String salt = PfLoginHelper.getSalt(dto.getUserName());
            // 设置盐值
            user.setForSalt(salt);
            // 加密密码
            user.setPassword(PfLoginHelper.getHashPsw(algorithmName, plaintext, hashIterations, salt));
            return pfUserDao.saveUser(user) == 1 ? true : false;
        } catch (BizRuntimeException e) {
            LoggerUtil.error(LOGGER, "【PfLoginServiceImpl-saveUser-error】, e.getMessage():{0}", e.getMessage());
            return false;
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "【PfLoginServiceImpl-saveUser-error】, e.getMessage():{0}", e.getMessage());
            return false;
        }
    }

    /**
     * 编辑用户
     * @param dto
     * @return
     */
    @Override
    public Boolean updateUser(RegisterDto dto) {
        LoggerUtil.info(LOGGER, "【PfLoginServiceImpl-updateUser-params】dto={0}", JSON.toJSONString(dto));
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(dto, user);
        return pfUserDao.updateUser(user) == 1 ? true : false;
    }

    /**
     * 登陆验证
     * @param dto
     * @return
     */
    @Override
    public boolean login(LoginDto dto) {
        LoggerUtil.info(LOGGER, "【PfLoginServiceImpl-login-params】dto={0}", JSON.toJSONString(dto));
        Subject subject = SecurityUtils.getSubject();
        //可以使用 subject.isAuthenticated() 以判断当前用户已经登录过了 此时可以直接通过subject.getSession()去获取我们放入session的信息了。
        String plaintext = PfLoginHelper.getPlaintext(privateKey, dto.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUserName(), plaintext);
        /* 登陆校验 */
        try {
            subject.login(token);
            /* 登录成功后设置session*/
            Session session = subject.getSession();
            UserInfo userInfo = (UserInfo) subject.getPrincipal();
            User user = new User();
            BeanUtils.copyProperties(userInfo, user);
            session.setAttribute(AuthConstant.CURRENT_USER, user);
            return true;
        } catch (UnknownAccountException e) {
            throw new BizRuntimeException(PfUserConstant.USER_NAME_ERROR, PfUserConstant.USER_NAME_ERROR_MSG);
        } catch (IncorrectCredentialsException e) {
            throw new BizRuntimeException(PfUserConstant.PASSWORD_ERROR, PfUserConstant.PASSWORD_ERROR_MSG);
        } catch (LockedAccountException lae) {
            throw new BizRuntimeException(PfUserConstant.LOCKED_ACCOUNT_ERROR, PfUserConstant.LOCKED_ACCOUNT_ERROR_MSG);
        } catch (ExpiredCredentialsException e) {
            throw new BizRuntimeException(PfUserConstant.EXPIRED_CREDENTIALS_ERROR, PfUserConstant.EXPIRED_CREDENTIALS_ERROR_MSG);
        } catch (UnauthorizedException e) {
            throw new BizRuntimeException(PfUserConstant.UNAUTHORIZED_ERROR, PfUserConstant.UNAUTHORIZED_ERROR_MSG);
        }catch (ExcessiveAttemptsException e) {
            throw new BizRuntimeException(PfUserConstant.LOGIN_FAILED_TIMES_ERROR, PfUserConstant.LOGIN_FAILED_TIMES_ERROR_MSG);
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            throw new BizRuntimeException("", "其他错误：" + e.getMessage());
        }
    }

    /**
     * 根据用户获取用户信息
     * @param userName
     * @return
     */
    @Override
    public UserInfo selectUser(String userName) {
        return pfUserDao.selectUser(userName);
    }

    /**
     * 修改密码
     * @param dto
     * @return
     */
    @Override
    public boolean updatePsw(UpdatePswDto dto) {
        LoggerUtil.info(LOGGER, "【PfLoginServiceImpl-updatePsw-params】dto={0}", JSON.toJSONString(dto));
         /* 参数校验 */
        Assert.notNull(dto.getUserId(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "user_id"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getUserName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "user_name"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getOldPassword()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "old_password"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getNewPassword()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "new_password"));

        /* 验证原密码 */
        String oldPlaintext = PfLoginHelper.getPlaintext(privateKey, dto.getOldPassword());
        UserInfo userInfo = pfUserDao.selectUserById(dto.getUserId());
        String oldCiphertext = PfLoginHelper.getHashPsw(algorithmName, oldPlaintext, hashIterations, userInfo.getForSalt());
        if (!StringUtils.equals(userInfo.getPassword(), oldCiphertext)) {
            // 原密码错误异常
            throw new BizRuntimeException(PfUserConstant.OLD_PASSWORD_ERROR, PfUserConstant.OLD_PASSWORD_ERROR_MSG);
        }

        /* 新密码保存 */
        UserInfo user = new UserInfo();
        user.setUserId(dto.getUserId());
        /* 密码处理 */
        String newPlaintext = PfLoginHelper.getPlaintext(privateKey, dto.getNewPassword());
        String salt = PfLoginHelper.getSalt(dto.getUserName());
        // 设置盐值
        user.setForSalt(salt);
        // 加密密码
        user.setPassword(PfLoginHelper.getHashPsw(algorithmName, newPlaintext, hashIterations, salt));
        return pfUserDao.updatePsw(user) == 1 ? true : false;
    }

    /**
     * 登出
     */
    @Override
    public boolean logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            LoggerUtil.debug(LOGGER, "登出用户：userName={0}", ((UserInfo) subject.getPrincipal()).getUserName());
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        return true;
    }


}
