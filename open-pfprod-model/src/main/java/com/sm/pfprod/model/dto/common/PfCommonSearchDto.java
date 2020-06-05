package com.sm.pfprod.model.dto.common;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfCommonSearchDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 问题内容
     */
    private String keywords;
}
