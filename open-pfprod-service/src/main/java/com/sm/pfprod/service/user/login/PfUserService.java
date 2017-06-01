package com.sm.pfprod.service.user.login;


import com.github.pagehelper.PageInfo;
import com.sm.pfprod.model.dto.user.login.LoginDto;
import com.sm.pfprod.model.dto.user.login.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.vo.user.PfUsersVo;

public interface PfUserService {

    /**
     * 获取用户列表
     * @return
     */
    PageInfo<PfUsersVo> listUsers(PfUserDto dto);

    /**
     * 保存用户信息
     * @param dto
     * @return
     */
    Boolean saveUser(RegisterDto dto);

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    Boolean updateUser(RegisterDto dto);

    /**
     * 登陆验证
     * @param dto
     * @return
     */
    boolean login(LoginDto dto);

    /**
     * 根据用户获取用户信息
     * @param userName
     * @return
     */
    UserInfo selectUser(String userName);

    /**
     * 修改密码
     * @param dto
     * @return
     */
    boolean updatePsw(UpdatePswDto dto);

    /**
     * 登出系统
     */
    boolean logout();
}
