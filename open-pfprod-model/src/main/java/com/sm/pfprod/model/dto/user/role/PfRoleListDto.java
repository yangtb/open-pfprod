package com.sm.pfprod.model.dto.user.role;

import com.sm.pfprod.model.entity.SysRole;

import java.io.Serializable;
import java.util.List;

public class PfRoleListDto implements Serializable {

    private static final long serialVersionUID = 5703704673324231853L;

    private List<SysRole> roles;

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
