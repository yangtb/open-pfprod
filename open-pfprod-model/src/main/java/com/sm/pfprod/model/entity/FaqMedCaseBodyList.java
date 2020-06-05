package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_检查_检查明细
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCaseBodyList implements Serializable {

    private static final long serialVersionUID = 1540450805283L;

    /**
     * 主键
     * 明细id
     */
    private Long idMedCaseList;

    /**
     * 病例组件案例id
     */
    private Long idMedCase;

    /**
     * 部位分类id
     */
    private Long idBodyCa;

    /**
     * 部位id
     */
    private Long idBody;

    /**
     * 部位描述
     */
    private String desBody;

    /**
     * 1. 正面 2. 背面 3. 胸部 4. 腹部 5. 头部
     */
    private String sdBody;

    /**
     * 1. 触诊 2. 听诊 3. 视诊 4. 叩诊 5. 设备
     */
    private String cdCheck;

    /**
     * 结果id
     */
    private Long idResult;

    /**
     * 结果描述
     */
    private String desResult;

    /**
     * 多媒体id
     */
    private String idMedia;

    /**
     * 是否需要说明理由
     */
    private String fgReason;

    /**
     * 是否根据病人回答反馈
     */
    private String fgBack;

    /**
     * 专家解读
     */
    private String desExpert;

    /**
     * 重载标志
     */
    private String fgCarried;

    /**
     * 删除标识：0=正常 1=删除
     */
    private String fgValid;

}
