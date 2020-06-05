package com.sm.pfprod.model.dto.user;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfUserDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8538601158882376369L;

    private String type;
    private String conditionValue;
    private Long idOrg;

    /**
     * 获取用户id
     */
    private Long userId;

    /**
     * 超级管理员
     */
    private boolean isSuper;

}
