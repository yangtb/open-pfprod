package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_试卷定义
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestpaper implements Serializable {

    private static final long serialVersionUID = 1540994867186L;


    /**
     * 主键
     * 试卷ID
     */
    private Long idTestpaper;

    /**
     * 试卷名称
     */
    private String naTestpaper;

    /**
     * 所属分类
     */
    private Long idTestpaperca;

    /**
     * 试卷描述
     */
    private String desTestpaper;

    /**
     * 归属机构
     */
    private Long idOrg;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 使用次数
     */
    private Integer count;

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
