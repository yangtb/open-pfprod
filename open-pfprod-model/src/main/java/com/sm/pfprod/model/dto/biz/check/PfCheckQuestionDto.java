package com.sm.pfprod.model.dto.biz.check;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfCheckQuestionDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 部位ID
     */
    private Long idBody;

    /**
     * 部位分类ID
     */
    private Long idBodyCa;

    /**
     * 部位描述
     */
    private String desBody;

    /**
     * 搜索关键字
     */
    private String keywords;

}
