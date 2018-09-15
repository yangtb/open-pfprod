package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统功能权限资源库
 */
public class SysFunction implements Serializable {

    private static final long serialVersionUID = 4513416522686508600L;

    private Long        id;                 // 功能权限资源ID，主键
    private String      name;               // 菜单名称
    private String      code;               // 功能权限资源编码，用来授权使用，例如："ROLE_USER_EDIT"，用户编辑权限资源
    private String      parentCode;         // 父功能权限资源ID
    private String      sysFunctionCode;    // 菜单功能资源的父子层级关系，例如：1，1_1，1_2_1等等
    private Integer     level;              // 菜单等级从1开始
    private String      functionUrl ;       // 菜单url
    private int         sortNum ;           // 推荐排序，默认999，数值从小到大排序
    private int         functionType;       // 功能权限资源类别。1表示菜单权限功能（有菜单层级，可视化控制及url拦截控制）；2表示按钮、链接功能权限资源（资源原子，可视化控制及url拦截控制）；3表示接口功能权限资源（纯url拦截控制）
    private String      platformType;       // 所属平台类别
    private String      status;             // 状态：有效(enabled)、无效(disabled) , 默认有效
    private int         iconType;           // 图标类型,0表示无，1表示iconfont的矢量图标，2表示url图标
    private String      iconSource;         // 图标源
    private Date        gmtCreate;          // 创建时间
    private Date        gmtModify;          // 更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSysFunctionCode() {
        return sysFunctionCode;
    }

    public void setSysFunctionCode(String sysFunctionCode) {
        this.sysFunctionCode = sysFunctionCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getFunctionType() {
        return functionType;
    }

    public void setFunctionType(int functionType) {
        this.functionType = functionType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public String getIconSource() {
        return iconSource;
    }

    public void setIconSource(String iconSource) {
        this.iconSource = iconSource;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}
