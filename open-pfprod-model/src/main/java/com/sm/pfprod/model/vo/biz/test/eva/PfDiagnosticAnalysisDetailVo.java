package com.sm.pfprod.model.vo.biz.test.eva;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDiagnosticAnalysisDetailVo implements Serializable {

    private static final long serialVersionUID = -3658629749787443176L;

    /**
     * 阶段
     */
    private Integer sdEvaEffciency;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 状态
     */
    private Integer flag;

}
