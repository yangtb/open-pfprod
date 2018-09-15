package com.sm.pfprod.model.enums;

import java.util.Map;

/**
 * @ClassName: ParamEnum
 * @Description: 枚举封装
 * @Author yangtongbin
 * @Date 2017/10/10 13:09
 */
public class PfEnum {

    private String                  groupCode;       // 枚举类型
    private String                  groupName;       // 枚举名称
    private Map<String, String>     dicMap;        // 枚举值 显示值 map

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, String> getDicMap() {
        return dicMap;
    }

    public void setDicMap(Map<String, String> dicMap) {
        this.dicMap = dicMap;
    }
}
