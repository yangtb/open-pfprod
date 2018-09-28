package com.sm.pfprod.service.user.login;


import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.dto.user.register.UserRegisterDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfUserService
 * @Description: 用户相关接口
 * @Author yangtongbin
 * @Date 2017/10/11 11:11
 */
public interface PfUserService {

    /**
     * 获取用户列表
     *
     * @return
     */
    PageResult listUsers(PfUserDto dto);

    /**
     * 保存用户信息
     *
     * @param dto
     * @return
     */
    boolean saveUser(RegisterDto dto);

    /**
     * 修改用户信息
     *
     * @param dto
     * @return
     */
    boolean updateUser(RegisterDto dto);

    /**
     * 删除用户
     *
     * @param dto 用户id集合
     * @return
     */
    boolean delUser(PfCommonListDto dto);

    /**
     * 删除用户
     *
     * @param dto 用户id集合
     * @return
     */
    boolean freezeUser(PfCommonListDto dto);

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    boolean updatePsw(UpdatePswDto dto);

    /**
     * 密码重置
     *
     * @return
     */
    boolean resetPsw(RegisterDto dto);

    /**
     * 根据用户获取用户信息
     *
     * @param userName
     * @return
     */
    UserInfo selectUser(String userName);

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    boolean registerUser(UserRegisterDto dto);

    /**
     * 发送验证码邮件
     *
     * @param email  邮箱
     * @param userId 用户id
     * @return
     */
    boolean sendRegisterEmailVcode(String email, Long userId);

}
