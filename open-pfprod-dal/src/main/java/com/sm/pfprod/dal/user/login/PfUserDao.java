package com.sm.pfprod.dal.user.login;

import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.vo.user.PfUsersVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserDao {

    /**
     * 获取用户列表
     * @return
     */
    List<PfUsersVo> listUsers();

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    Integer saveUser(UserInfo user);

    /**
     * 编辑用户信息
     * @param user
     * @return
     */
    Integer updateUser(UserInfo user);

    /**
     * 根据用户获取用户信息
     * @param userName
     * @return
     */
    UserInfo selectUser(@Param("userName") String userName);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    UserInfo selectUserById(@Param("userId") Long userId);

    /**
     * 修改密码
     * @param user
     * @return
     */
    Integer updatePsw(UserInfo user);

}
