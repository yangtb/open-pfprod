package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictionary implements Serializable {

    private static final long serialVersionUID = -6793337107156687509L;

    private Long        id;             // ID
    private String      groupName;      // 分组名称
    private String      groupCode;      // 字典分组编码，用于区分一组字典
    private String      dictName;       // 显示名称
    private String      dictCode;       // 数据值编码
    private String      extValue ;      // 扩展数据项
    private Date        gmtCreate;      // 创建时间
    private Date        gmtModify;      // 更新时间
    private String      status;         // 状态：有效(enabled)、无效(disabled)、通过(pass)、失败(fail)、已发布(publish)
    private Integer     sortNum;        // 推荐排序，默认999，数值从小到大排序
    private String      operator;       // 操作人员
    private String      remark;         // 备注
    private String      sysType;        // 作用业务与系统 all-所有

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getExtValue() {
        return extValue;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }
}
