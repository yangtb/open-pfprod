package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_病历标签
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedTag implements Serializable {

    private static final long serialVersionUID = 1541404303662L;


    /**
     * 主键
     * 病历标签id
     */
    private Long idMedTag;

    /**
     * 所属病历
     */
    private Long idMedicalrec;

    /**
     * 所属病历名称
     */
    private String caseName;

    /**
     * 标签id
     */
    private Long idTag;

    /**
     * 病历组件案例id
     */
    private Long idMedCase;

    /**
     * 为1时保存病例与标签关联管理
     */
    private String tagFlag;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 原病历组件案例id
     */
    private Long oldIdMedCase;

    /**
     * 机构id
     */
    private Long idOrg;

    private String cdMedAsse;

}
