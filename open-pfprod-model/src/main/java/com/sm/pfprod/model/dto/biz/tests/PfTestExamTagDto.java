package com.sm.pfprod.model.dto.biz.tests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestExamTagDto implements Serializable {

    private static final long serialVersionUID = -1707262746906227797L;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 病例组件编码
     */
    private String cdMedAsse;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 执行时是否显示专家解读
     */
    private String fgShowExec;

    /**
     * 执行完成时是否显示专家解读
     */
    private String fgShowExecFinsh;

    /**
     * 检查部位
     */
    private String sdBody;

}
