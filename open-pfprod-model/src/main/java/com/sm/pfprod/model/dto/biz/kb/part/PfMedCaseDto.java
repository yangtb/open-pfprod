package com.sm.pfprod.model.dto.biz.kb.part;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfMedCaseDto extends PageParam implements Serializable {

    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 用例名称
     */
    private String name;

    /**
     * 平台标识
     */
    private String fgPlat;

    /**
     * 病例组件编码
     */
    private String cdMedAsse;

    /**
     * 创建人
     */
    private String creator;


}
