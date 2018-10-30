package com.sm.pfprod.model.enums;

/**
 * @ClassName: SysDicGroupEnum
 * @Description: 字典分组枚举
 * @Author yangtongbin
 * @Date 2018/8/26 23:16
 */
public enum SysDicGroupEnum {

    SEX("SEX", "性别"),
    AREA_CODE("AREA_CODE", "城市编码"),
    SYS_PARAM_BIZ_MODUAL("SYS_PARAM_BIZ_MODULE", "系统参数-作用模块"),
    BANNER_POSITION("BANNER_POSITION", "banner位"),
    EXAM_WAYS("EXAM_WAYS", "检查方式"),
    BODY_POSITION("BODY_POSITION", "检查题库"),
    CLINIC_TEMPLATE_TAG("CLINIC_TEMPLATE_TAG", "模板标签"),
    CASE_HISTORY_LEVEL("CASE_HISTORY_LEVEL", "病例级别"),
    CASE_HISTORY_USE("CASE_HISTORY_USE", "病例用途"),
    IMPORT_TYPE("IMPORT_TYPE", "嵌入类型"),
    ALGORITHM_DEFINE("ALGORITHM_DEFINE", "算法定义"),
    CASE_PART_PAGE("CASE_PART_PAGE", "病例组件页面"),
    SD_EVA("SD_EVA", "评估阶段"),
    SD_EVA_ORDER("SD_EVA_ORDER", "评估类别"),
    SD_NURS_ROUT("SD_NURS_ROUT", "护理常规"),
    CD_NURS_LEVEL("CD_NURS_LEVEL", "护理级别"),
    SD_DIET("SD_DIET", "饮食"),
    SD_POSITION("SD_POSITION", "体位");


    private String code;
    private String desc;

    SysDicGroupEnum(String code, String desc) {
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
