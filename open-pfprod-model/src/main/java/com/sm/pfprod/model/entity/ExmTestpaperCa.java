package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_试卷分类
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestpaperCa implements Serializable {

    private static final long serialVersionUID = 1540994871345L;

    /**
     * 主键
     * 试卷分类ID
     */
    private Long idTestpaperca;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级分类ID
     */
    private Long idPar;

    /**
     * 归属机构
     */
    private Long idOrg;

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
     * 排除时间
     */
    private Date gmtModify;


}
