package com.sm.pfprod.model.vo.role;

import java.io.Serializable;
import java.util.Date;

public class PfRoleVo implements Serializable {

    private static final long serialVersionUID = 3352454116018853943L;

    private Long        roleId;         // 角色ID
    private String      name;           // 角色名称
    private String      resume;         // 角色描述
    private int         state;          // 是否有效(0-有效 1-无效)
    private String      operator;       // 创建人
    private Date        gmtCreate;      // 创建时间
    private boolean     checked;        // 是否拥有某权限

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
