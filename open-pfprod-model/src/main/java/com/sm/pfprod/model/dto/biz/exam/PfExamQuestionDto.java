package com.sm.pfprod.model.dto.biz.exam;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfExamQuestionDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;


    /**
     * 检验分类ID
     */
    private Long idInspect;
    /**
     * 项目ID
     */
    private Long idInspectItem;

    /**
     * 项目名称
     */
    private String naItem;

    /**
     * 搜索关键字
     */
    private String keywords;
}
