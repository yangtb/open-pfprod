package com.sm.pfprod.model.dto.system.message;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfMessageDto extends PageParam implements Serializable {

    private static final long serialVersionUID = 5654966115931979418L;

    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板类型
     */
    private String templateType;
    /**
     * 删除标识
     */
    private String isDeleted;

}
