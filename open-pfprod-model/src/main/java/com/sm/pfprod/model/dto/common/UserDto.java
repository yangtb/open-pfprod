package com.sm.pfprod.model.dto.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = 8385384321073501601L;

    private Long        userId;         // 管理员ID
    private String      userName;       // 管理员用户名
    private String      email;          // 电邮
    private String      phoneNo;        // 联系电话
    private String      roleType;       // 用户角色类型

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
