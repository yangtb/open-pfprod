package com.sm.pfprod.model.dto.biz.tests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestExamDto implements Serializable {

    private static final long serialVersionUID = -5117680801912785399L;

    private Long idTestexecResult;

    /**
     * 测试明细ID
     */
    private Long idTestplanDetail;

    /**
     * 测试计划id
     */
    private Long idTestplan;

    /**
     * 学生id
     */
    private Long idStudent;

    /**
     * 病例模板id
     */
    private Long idDemo;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 病例组件案例id
     */
    private Long idMedCase;

    /**
     * 试卷id
     */
    private Long idTestpaper;

}
