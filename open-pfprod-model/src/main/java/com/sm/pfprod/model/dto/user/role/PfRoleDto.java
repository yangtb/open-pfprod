package com.sm.pfprod.model.dto.user.role;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

/**
 * 角色表
 */
public class PfRoleDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -2349611519982619401L;

    private int         state;
    private String      conditionValue;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
