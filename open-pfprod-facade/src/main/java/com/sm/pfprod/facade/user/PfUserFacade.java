package com.sm.pfprod.facade.user;

import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfUserFacade
 * @Description: 用户模块
 * @Author yangtongbin
 * @Date 2017/9/29 14:55
 */
public interface PfUserFacade {

    /**
     * 用户列表
     *
     * @param dto
     * @return
     */
    PageResult listUsers(PfUserDto dto);

    /**
     * 新增用户
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
     * @param users 用户ID
     * @return
     */
    boolean delUser(List<Long> users);

    /**
     * 删除用户
     *
     * @param users 用户ID
     * @return
     */
    boolean freezeUser(List<Long> users);

}
