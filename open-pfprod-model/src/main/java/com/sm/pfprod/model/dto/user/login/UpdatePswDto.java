package com.sm.pfprod.model.dto.user.login;

import java.io.Serializable;

public class UpdatePswDto implements Serializable {

    private static final long serialVersionUID = 6379879533868883447L;

    private Long        userId;          // 用户id
    private String      userName;        // 用户名
    private String      oldPassword;     // 原密码
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
