package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqEvaCaseItemThorough implements Serializable {

    private static final long serialVersionUID = -4222740486577884449L;

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
     * 1 问诊 2 检查 3 检验
     */
    private String sdEvaMust;

    /**
     * 疾病id
     */
    private Long idDie;

    /**
     * 覆盖检查_问诊
     */
    private Long idInques;

    /**
     * 覆盖检查_检查
     */
    private Long idBody;

    /**
     * 覆盖检查_检验
     */
    private Long idInspectItem;

    /**
     * 字典文本
     */
    private String desText;

    /**
     * crs标志
     */
    private String fgCrs;
}
