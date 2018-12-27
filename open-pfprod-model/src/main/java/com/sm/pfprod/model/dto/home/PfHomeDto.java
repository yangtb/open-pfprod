package com.sm.pfprod.model.dto.home;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfHomeDto implements Serializable {

    /**
     * 超级管理员
     */
    private boolean isSuper;
    /**
     * 匿名用户
     */
    private boolean isAnonymousUser;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 当前用户所在机构id
     */
    private Long idOrg;
}
