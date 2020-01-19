package com.sm.pfprod.model.dto.biz.kb.assess;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfAssessCommonitemDto implements Serializable {

    private static final long serialVersionUID = -6084909718903392220L;

    private Long id;

    private String name;

}
