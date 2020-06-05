package com.sm.pfprod.model.dto.biz.inquisition;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfInquisitionQuestionDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 问题ID
     */
    private Long idInques;
    /**
     * 问诊分类ID
     */
    private Long idInquesCa;

    /**
     * 问题内容
     */
    private String desInques;

    /**
     * 搜索关键字
     */
    private String keywords;

    /**
     * 扩展id
     */
    private Long extId;

    /**
     * 过滤标识
     */
    private Long idMedCase;

}
