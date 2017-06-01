package com.sm.pfprod.model.dto.user.role;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 角色表
 */
public class PfRoleDto implements Serializable {

    private static final long serialVersionUID = -2349611519982619401L;

    @JSONField(name = "role_id")
    private Long        roleId;         // 角色ID
    private String      name;           // 角色名称
    private String      resume;         // 角色描述
    @JSONField(name = "creator_id")
    private Long        creatorId;      // 创建人ID

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
