package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试计划
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestplan implements Serializable {

    private static final long serialVersionUID = -1760922473486478673L;

    /**
     * 主键
     * 测试计划ID
     */
    private Long idTestplan;

    /**
     * 测试计划名称
     */
    private String naTestplan;

    /**
     * 测试计划描述
     */
    private String desTestplan;

    /**
     * 测试试卷ID
     */
    private Long idTestpaper;

    /**
     * 试卷名称
     */
    private String idTestpaperText;

    /**
     * 归属机构
     */
    private Long idOrg;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 0 计划 1 部分执行 2 完成
     */
    private String sdTestplan;

    /**
     * 0 未激活 1 已激活
     */
    private String fgActive;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人员
     */
    private String operator;

    /**
     * 修改时间
     */
    private Date gmtModify;

}
