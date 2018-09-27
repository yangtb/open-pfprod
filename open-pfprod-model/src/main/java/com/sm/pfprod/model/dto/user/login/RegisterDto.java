package com.sm.pfprod.model.dto.user.login;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"password"})
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
    private String      operator;       // 操作人员

}
