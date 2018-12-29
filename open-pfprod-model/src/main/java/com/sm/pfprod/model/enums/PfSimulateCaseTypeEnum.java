package com.sm.pfprod.model.enums;

/**
 * @ClassName: PfSimulateCaseTypeEnum
 * @Description: 模拟病例类型
 * @Author yangtongbin
 * @Date 2018-12-08
 */
public enum PfSimulateCaseTypeEnum {

    CLOSE("1", "不开放"),
    RANDOM("2", "随机"),
    DESIGNATED_LIBRARY("3", "从指定病例库中选择");

    private String code;
    private String desc;

    PfSimulateCaseTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
