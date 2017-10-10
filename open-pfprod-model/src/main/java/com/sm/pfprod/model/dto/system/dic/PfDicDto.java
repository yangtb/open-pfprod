package com.sm.pfprod.model.dto.system.dic;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

public class PfDicDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -3709105679554437642L;

    private String      dicName;        // 字典名称
    private String      enumName;       // 枚举名称
    private String      groupCode;      // 分组编码

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
