package com.sm.pfprod.model.vo.role;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PfRoleVo implements Serializable {

    private static final long serialVersionUID = 3352454116018853943L;

    @JSONField(name = "role_id")
    private Long        roleId;         // 角色ID
    private String      name;           // 角色名称
    private String      resume;         // 角色描述
    private int         state;          // 是否有效(0-有效 1-无效)
    @JSONField(name = "creator_id")
    private Long        creatorId;      // 创建人ID
    @JSONField(name = "gmt_create")
    private Date        gmtCreate;      // 创建时间
    @JSONField(name = "menuId_list")
    private List<Long>  menuIdList;     // 角色对应菜单id

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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
