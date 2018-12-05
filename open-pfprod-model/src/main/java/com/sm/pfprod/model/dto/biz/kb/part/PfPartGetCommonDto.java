package com.sm.pfprod.model.dto.biz.kb.part;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfPartGetCommonDto implements Serializable {

    private static final long serialVersionUID = 4010130212892829241L;

    private String idMedCase;
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
