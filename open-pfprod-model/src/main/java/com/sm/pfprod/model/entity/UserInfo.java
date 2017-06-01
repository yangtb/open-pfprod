package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8710243137962838732L;

    private Long        userId;         // 管理员ID
    private String      userName;       // 管理员用户名
    private String      password;       // 密码
    private String      forSalt;        // 密码盐值
    private String      email;          // 电邮
    private String      phoneNo;        // 联系电话
    private String      isBlock;        // 是否锁定
    private int         isFirst;        // 是否首次为0, 否则为1
    private String      roleType;       // 用户角色类型：1超级管理员，2普通管理员
    private String      roleDesc;       // 管理员角色描述
    private String      realName;       // 真实姓名
    private String      remark;         // 备注
    private Date        lastLoginTime;  // 最后登录时间
    private String      isDeleted;      // 删除标示，N未删除 Y-已删除
    private Date        gmtCreate;      // 创建时间
    private Date        gmtModify;      // 更新时间

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForSalt() {
        return forSalt;
    }

    public void setForSalt(String forSalt) {
        this.forSalt = forSalt;
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

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}
