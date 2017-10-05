package com.sm.pfprod.model.dto.user.login;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class RegisterDto implements Serializable {

    private static final long serialVersionUID = -6354572733503100572L;

    @JSONField(name = "user_id")
    private Long        userId;         // 用户id
    @JSONField(name = "user_name")
    private String      username;       // 用户名
    private String      password;       // 密码
    private String      email;          // 电邮
    @JSONField(name = "phone_no")
    private String      phoneNo;        // 联系电话
    @JSONField(name = "role_type")
    private String      roleType;       // 用户角色类型：1超级管理员，2普通管理员
    private boolean     enabled;        // 是否锁定
    @JSONField(name = "real_name")
    private String      realName;       // 真实姓名
    private int         sex;            // 性别
    private String      remark;         // 备注
    private List<Long>  roles;          // 用户角色

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
}
