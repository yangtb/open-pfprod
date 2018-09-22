package com.sm.pfprod.model.dto.system.org;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfOrgDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8065165575922718762L;

    private String name;
    private String fgActive;
    private String fgValid;

}
