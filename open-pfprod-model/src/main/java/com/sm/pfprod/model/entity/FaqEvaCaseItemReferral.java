package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_病例评估_评估项_拟诊
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqEvaCaseItemReferral implements Serializable {

    private static final long serialVersionUID = 1540712688727L;

    /**
     * 主键
     * 明细id
     */
    private Long idEvaCaseItemList;

    /**
     * 病例评估项id
     */
    private Long idEvaCaseItem;

    /**
     * 1. 问诊 2. 检查 3. 检验
     */
    private String sdEvaReferral;

    /**
     * 疾病id
     */
    private Long idDie;

    /**
     * 疾病名称
     */
    private String idDieText;

    /**
     * 疾病分类标记，如果为1 表示存储的是疾病目录，如果为2 表示存储的是疾病
     */
    private String fgDieClass;

    /**
     * crs标志
     */
    private String fgCrs;
}
