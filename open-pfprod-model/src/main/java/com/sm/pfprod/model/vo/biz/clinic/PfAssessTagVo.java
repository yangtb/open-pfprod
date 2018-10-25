package com.sm.pfprod.model.vo.biz.clinic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfAssessTagVo implements Serializable {

    private static final long serialVersionUID = -4057393469549135787L;

    private Long idTag;
    private String name;
    private String cdEvaAsse;
    private String path;
    private String script;
    /**
     * 制作病例时是否显示
     */
    private String fgShowMake;

    /**
     * 执行时是否显示
     */
    private String fgShowExec;
}
