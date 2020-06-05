package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfEmailSetDto
 * @Description: 发送邮件参数设置
 * @Author yangtongbin
 * @Date 2018/9/16 16:26
 */
@Setter
@Getter
@ToString
public class PfEmailSet implements Serializable {

    private static final long serialVersionUID = -6768603893343433790L;

    private String host;
    private String sender;
    private String userName;
    private String nickname;
    private String password;
    private String sendSwitch;
}
