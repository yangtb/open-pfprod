package com.sm.pfprod.model.dto.user.login;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UpdatePswDto implements Serializable {

    private static final long serialVersionUID = 6379879533868883447L;

    @JSONField(name = "user_id")
    private Long        userId;          // 用户id
    @JSONField(name = "user_name")
    private String      userName;        // 用户名
    @JSONField(name = "old_password")
    private String      oldPassword;     // 原密码
    @JSONField(name = "new_password")
    private String      newPassword;     // 新密码

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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
