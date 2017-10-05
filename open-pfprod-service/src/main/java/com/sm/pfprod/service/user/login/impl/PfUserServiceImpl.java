package com.sm.pfprod.service.user.login.impl;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.dal.user.login.PfUserDao;
import com.sm.pfprod.model.dto.common.UserDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.LoginDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * 注册、登陆
 */
@Service("pfUserService")
public class PfUserServiceImpl implements PfUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfLoginHelper.class);

    @Resource
    private PfUserDao pfUserDao;

    @Value(value = "${private_key}")
    private String privateKey;             // 私钥
    @Value(value = "${algorithmName}")
    private String algorithmName;          // 加密算法
    @Value(value = "${hashIterations}")
    private int hashIterations;         // 加密次数

    @Override
    public List<PfUsersVo> listUsers(PfUserDto dto) {
        return pfUserDao.listUsers(dto);
    }

    @Override
    public Long countUsers(PfUserDto dto) {
        return pfUserDao.countUsers();
    }

    @Transactional
    @Override
    public boolean saveUser(RegisterDto dto) {
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(dto, user);
        // 新增用户
        pfUserDao.saveUser(user);
        // 插入用户角色
        pfUserDao.saveUserRole(dto.getRoles(), user.getUserId());
        return true;
    }

    @Override
    public boolean isExistUser(String userName) {
        return pfUserDao.isExistUser(userName);
    }

    /**
     * 编辑用户
     *
     * @param dto
     * @return
     */
    @Override
    public boolean updateUser(RegisterDto dto) {
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(dto, user);
        // 修改用户信息
        pfUserDao.updateUser(user);
        // 删除用户角色
        pfUserDao.delUserRole(dto.getUserId());
        // 插入用户角色
        pfUserDao.saveUserRole(dto.getRoles(), dto.getUserId());
        return true;
    }

    @Override
    public boolean delUser(List<Long> users) {
        return pfUserDao.delUser(users) >= 1 ? true : false;
    }

    @Override
    public boolean freezeUser(List<Long> users) {
        return pfUserDao.freezeUser(users) >= 1 ? true : false;
    }

    /**
     * 登陆验证
     *
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
            UserDto user = new UserDto();
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
        } catch (ExcessiveAttemptsException e) {
            throw new BizRuntimeException(PfUserConstant.LOGIN_FAILED_TIMES_ERROR, PfUserConstant.LOGIN_FAILED_TIMES_ERROR_MSG);
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            throw new BizRuntimeException("", "其他错误：" + e.getMessage());
        }
    }

    /**
     * 根据用户获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public UserInfo selectUser(String userName) {
        return pfUserDao.selectUser(userName);
    }

    /**
     * 修改密码
     *
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
        String oldCiphertext = PfLoginHelper.getHashPsw(algorithmName, oldPlaintext, hashIterations, userInfo.getSalt());
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
        user.setSalt(salt);
        // 加密密码
        user.setPassword(PfLoginHelper.getHashPsw(algorithmName, newPlaintext, hashIterations, salt));
        return pfUserDao.updatePsw(user) == 1 ? true : false;
    }

    @Override
    public String genEncriptPwd(String rawPwd, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = "";
        }
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder(salt);
        String encodedPassword = passwordEncoder.encode(rawPwd);
        return encodedPassword;
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println(salt + ", " + genEncriptPwd1("ytb316", salt));
    }

    public static String genEncriptPwd1(String rawPwd, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = "";
        }
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder(salt);
        String encodedPassword = passwordEncoder.encode(rawPwd);
        return encodedPassword;
    }

    /**
     * 登出
     */
    @Override
    public boolean logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            LoggerUtil.debug(LOGGER, "登出用户：userName={0}", ((UserInfo) subject.getPrincipal()).getUsername());
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        return true;
    }


}
