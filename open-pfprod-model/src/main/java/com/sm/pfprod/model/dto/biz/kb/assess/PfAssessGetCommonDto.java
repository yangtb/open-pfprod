package com.sm.pfprod.model.dto.biz.kb.assess;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfAssessGetCommonDto implements Serializable {

    private static final long serialVersionUID = 4010130212892829241L;

    private String cdEvaAsse;
    private String idEvaCase;
    private Integer showForm;
    private String showBtn;
    private String tagFlag;

    /**
     * 病历id
     */
    private Long idMedicalrec;

    /**
     * 病历名称
     */
    private String caseName;

    /**
     * 标签id
     */
    private Long idTag;
}
