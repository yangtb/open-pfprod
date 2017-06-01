package com.sm.pfprod.model.dto.user.login;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 登陆dto
 */
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 4144204267241614789L;

    @JSONField(name = "user_name")
    private String      userName;       // 管理员用户名
    private String      password;       // 密码

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginDto{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
