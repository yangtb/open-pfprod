package com.sm.pfprod.model.dto.user.register;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class RegisterVcodeDto implements Serializable {

    private static final long serialVersionUID = 800487833241656361L;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 图片验证码
     */
    private String photoVercode;


}
