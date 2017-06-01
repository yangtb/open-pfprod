package com.sm.pfprod.model.dto.user.login;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class RegisterDto implements Serializable {

    private static final long serialVersionUID = -6354572733503100572L;

    @JSONField(name = "user_id")
    private String      userId;       // 用户id
    @JSONField(name = "user_name")
    private String      userName;       // 用户名
    private String      password;       // 密码
    private String      email;          // 电邮
    @JSONField(name = "phone_no")
    private String      phoneNo;        // 联系电话
    @JSONField(name = "role_type")
    private String      roleType;       // 用户角色类型：1超级管理员，2普通管理员
    @JSONField(name = "is_block")
    private String      isBlock;        // 是否锁定
    @JSONField(name = "real_name")
    private String      realName;       // 真实姓名
    private String      remark;         // 备注
    private List<Long>  roles;          // 用户角色

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
