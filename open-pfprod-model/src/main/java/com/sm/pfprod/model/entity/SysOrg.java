package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysOrg
 * @Description: 机构实体
 * @Author yangtongbin
 * @Date 2018/9/20 15:30
 */
@Setter
@Getter
@ToString
public class SysOrg implements Serializable {

    private static final long serialVersionUID = 1537428265775L;

    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 描述
     */
    private String des;

    /**
     * 联系地址
     */
    private String addr;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电邮
     */
    private String email;

    /**
     * 0=机构，1=平台
     */
    private String fgPlat;

    /**
     * 0=未激活，1=已激活
     */
    private String fgActive;

    /**
     * isNullAble:1
     */
    private String fgValid;

    /**
     * NULL 为不限期
     */
    private String gmtValid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后修改人
     */
    private String operator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

}
