package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SysParam implements Serializable {

    private static final long serialVersionUID = 8617982017538738949L;

    private Long        id;             // ID
    private String      paramCode;      // 参数编码
    private String      paramName;      // 参数名称
    private String      paramValue;     // 参数值
    private String      defaultValue;   // 默认值
    private String      dataType;       // 参数值数据类型(string-字串，int-整数 float-小数 datetime-日期与时间-yyyy-MM-dd HH:mm:ss,date-日期yyyy-MM-dd), 多值(multi)
    private String      extendValue;    // 扩展数据项
    private String      sysType;        // 作用业务与系统 all-所有
    private String      bizModule;      // 作用业务模块
    private Date        gmtCreate;      // 创建时间
    private Date        gmtModify;      // 更新时间
    private String      status;         // 状态：有效(enabled)、无效(disabled)、通过(pass)、失败(fail)、已发布(publish)
    private Integer     sortNum;        // 推荐排序，默认999，数值从小到大排序
    private String      operator;       // 操作人员
    private String      remark;         // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getExtendValue() {
        return extendValue;
    }

    public void setExtendValue(String extendValue) {
        this.extendValue = extendValue;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getBizModule() {
        return bizModule;
    }

    public void setBizModule(String bizModule) {
        this.bizModule = bizModule;
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
}
