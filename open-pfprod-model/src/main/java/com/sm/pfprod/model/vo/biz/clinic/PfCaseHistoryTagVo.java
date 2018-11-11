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
    private String cdMedAsse;
    private String path;
    /**
     * 病历组件嵌入代码
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
}
