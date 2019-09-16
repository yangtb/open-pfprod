package com.sm.pfprod.model.vo.biz.clinic;

import com.sm.pfprod.model.entity.FaqMedTag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfCaseHistoryTagVo extends FaqMedTag implements Serializable {

    private static final long serialVersionUID = -4057393469549135787L;

    private Long idTag;
    private String name;
    private String pName;
    private String cdMedAsse;
    private String path;
    /**
     * 病例组件嵌入代码
     */
    private String script;
    /**
     * 嵌入代码_病例执行
     */
    private String scriptExec;
    /**
     * 制作病例时是否显示
     */
    private String fgShowMake;

    /**
     * 执行时是否显示
     */
    private String fgShowExec;


    /**
     * 排序
     */
    private Integer sort;

    /**
     * 流程类型 1 并行 2 串行
     */
    private String sdProcess;

    /**
     * 串行序号(串行必填，并行为空或0)
     */
    private Integer processSerialno;
}
