package com.sm.pfprod.model.dto.biz.clinic;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfClinicTemplateDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7600891889017067299L;

    /**
     * 部位ID
     */
    private Long idDemo;

    /**
     * 部位分类ID
     */
    private Long idDemoCa;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 执行时是否显示
     */
    private String fgShowExec;
}
