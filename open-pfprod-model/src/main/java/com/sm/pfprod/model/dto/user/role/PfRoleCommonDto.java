package com.sm.pfprod.model.dto.user.role;

import java.io.Serializable;

/**
 * @ClassName: PfRoleCommonDto
 * @Description: 角色通用参数
 * @Author yangtongbin
 * @Date 2017/9/28 16:37
 */
public class PfRoleCommonDto implements Serializable {

    private static final long serialVersionUID = -2956233063776652089L;

    private Long        roleId;             // 角色ID


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

 }
