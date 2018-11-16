package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Setter
@Getter
@ToString
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8710243137962838732L;

    private Long        userId;         // 管理员ID
    private String      username;       // 管理员用户名
    private String      password;       // 密码
    private String      salt;           // 密码盐值
    private String      email;          // 电邮
    private String      phoneNo;        // 联系电话
    private boolean     enabled;        // 是否启用,1启用 0停用
    private int         isFirst;        // 是否首次为0, 否则为1
    private String      roleType;       // 用户角色类型：1超级管理员，2普通管理员
    private String      roleDesc;       // 管理员角色描述
    private String      realName;       // 真实姓名
    private Integer         sex;            // 性别
    private String      remark;         // 备注
    private Long        idOrg;          // 机构ID
    private Date        lastLoginTime;  // 最后登录时间
    private String      isDeleted;      // 删除标示，N未删除 Y-已删除
    private String      operator;       // 操作人员
    private Date        gmtCreate;      // 创建时间
    private Date        gmtModify;      // 更新时间
    /** 当前用户所在机构级别 */
    private String fgPlat;

    /** 当前用户所在机构状态 */
    private String fgActive;
}
