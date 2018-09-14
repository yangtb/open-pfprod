package com.sm.pfprod.model.vo.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
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
    /**
     * 操作人员
     */
    private String      operator;
    /**
     * 创建时间
     */
    @JSONField(name = "gmt_create")
    private Date        gmtCreate;
    /**
     * 修改时间
     */
    @JSONField(name = "gmt_modify")
    private Date        gmtModify;

}
