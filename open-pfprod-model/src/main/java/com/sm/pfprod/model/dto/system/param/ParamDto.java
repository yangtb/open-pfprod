package com.sm.pfprod.model.dto.system.param;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

public class ParamDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7532471455664606363L;

    private String      paramName;          // 字典名称
    private String      status;             // 状态

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
