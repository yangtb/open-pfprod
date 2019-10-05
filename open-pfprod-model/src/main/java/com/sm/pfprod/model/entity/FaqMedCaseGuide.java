package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_评估指南
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCaseGuide extends FaqMedTag implements Serializable {

    private static final long serialVersionUID = 9018067625482596938L;

    /**
     * 主键
     * 病例组件案例id
     */
    private Long idMedCase;

    /**
     * 评估指南
     */
    private String guideContent;


    /**
     * 评估指南说明utl
     */
    private String guideNotesUrl;

}
