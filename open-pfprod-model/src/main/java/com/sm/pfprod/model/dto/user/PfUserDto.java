package com.sm.pfprod.model.dto.user;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

public class PfUserDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8538601158882376369L;

    private String type;
    private String conditionValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
