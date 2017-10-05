package com.sm.pfprod.model.vo.user;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class PfUsersVo implements Serializable {

    private static final long serialVersionUID = -1951354599783080290L;

    @JSONField(name = "user_id")
    private Long        userId;         // 用户ID
    @JSONField(name = "user_name")
    private String      userName;       // 用户名
    private String      email;          // 电邮
    @JSONField(name = "phone_no")
    private String      phoneNo;        // 联系电话
    private int         sex;            // 性别
    private boolean     enabled;        // 是否锁定
    @JSONField(name = "is_first")
    private int         isFirst;        // 是否首次为0, 否则为1
    @JSONField(name = "role_type")
    private String      roleType;       // 用户角色类型：1超级管理员，2普通管理员
    @JSONField(name = "real_name")
    private String      realName;       // 真实姓名
    private String      remark;         // 备注
    @JSONField(name = "last_login_time")
    private Date        lastLoginTime;  // 最后登录时间
    @JSONField(name = "is_deleted")
    private String      isDeleted;      // 删除标示，N未删除 Y-已删除
    @JSONField(name = "gmt_create")
    private Date        gmtCreate;      // 创建时间

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
