package com.sm.pfprod.model.dto.biz.disease;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDiseaseInfoDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 查询类型
     */
    private int type;
    /**
     * 查询条件
     */
    private String queryCondition;
    /**
     * 激活状态
     */
    private String fgActive;
    /**
     * 疾病名称
     */
    private String name;
    /**
     * ICD编码
     */
    private String icd;
    /**
     * 拼音助记符
     */
    private String pinyin;
    /**
     * 搜索关键字
     */
    private String keywords;

    /**
     * 目录id
     */
    private Long catalogueId;
}
