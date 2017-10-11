package com.sm.pfprod.service.user.login;


import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.vo.user.PfUsersVo;

import java.security.interfaces.RSAPrivateKey;
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
     * @param rawPwd 原始密码
     * @param salt   盐值
     * @return
     */
    String genEncriptPwd(String rawPwd, String salt);

    /**
     * 验证密码
     *
     * @param rawPwd     原密码
     * @param salt       盐值
     * @param encriptPwd 加密密码
     * @return
     */
    boolean matchPassword(String rawPwd, String salt, String encriptPwd);

}
