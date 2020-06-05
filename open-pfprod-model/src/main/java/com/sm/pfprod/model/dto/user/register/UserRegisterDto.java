package com.sm.pfprod.model.dto.user.register;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString(exclude = {"password"})
public class UserRegisterDto implements Serializable {

    private static final long serialVersionUID = -8667177751976950453L;

    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 用户名
     */
    private String username;

    /**
     * 联系方式
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱验证码
     */
    private String emailVercode;

    /**
     * 图片验证码
     */
    private String photoVercode;

    /**
     * 密码
     */
    private String password;
}
