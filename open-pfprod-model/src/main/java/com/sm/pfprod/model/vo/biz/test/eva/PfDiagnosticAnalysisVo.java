package com.sm.pfprod.model.vo.biz.test.eva;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDiagnosticAnalysisVo implements Serializable {

    private static final long serialVersionUID = -3658629749787443176L;

    /**
     * id
     */
    private Long idEvaCaseItem;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 疾病项目
     */
    private String idDieStr;

    /**
     * 标识
     */
    private String flag;

    /**
     * 类型 ：1=确诊项 2=排除拟诊项
     */
    private Integer type;

}
