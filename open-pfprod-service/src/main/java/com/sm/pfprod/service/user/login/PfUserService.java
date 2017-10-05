package com.sm.pfprod.service.user.login;


import com.sm.pfprod.model.dto.user.login.LoginDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.user.PfUsersVo;

import java.util.List;

public interface PfUserService {

    /**
     * 获取用户列表
     *
     * @return
     */
    List<PfUsersVo> listUsers(PfUserDto dto);

    /**
     * 用户总数
     */
    Long countUsers(PfUserDto dto);


    /**
     * 保存用户信息
     *
     * @param dto
     * @return
     */
    boolean saveUser(RegisterDto dto);


    /**
     * 判断用户名是否存在
     *
     * @param userName
     * @return
     */
    boolean isExistUser(String userName);

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
     * @param users 用户id集合
     * @return
     */
    boolean delUser(List<Long> users);

    /**
     * 删除用户
     *
     * @param users 用户id集合
     * @return
     */
    boolean freezeUser(List<Long> users);

    /**
     * 登陆验证
     *
     * @param dto
     * @return
     */
    boolean login(LoginDto dto);

    /**
     * 根据用户获取用户信息
     *
     * @param userName
     * @return
     */
    UserInfo selectUser(String userName);

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    boolean updatePsw(UpdatePswDto dto);

    /**
     * 密码加密
     *
     * @param rawPwd
     * @param salt
     * @return
     */
    String genEncriptPwd(String rawPwd, String salt);

    /**
     * 登出系统
     */
    boolean logout();
}
