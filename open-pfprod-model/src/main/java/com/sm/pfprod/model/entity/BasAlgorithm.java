package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_评估算法
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasAlgorithm implements Serializable {

    private static final long serialVersionUID = 1539331943593L;

    /**
     * 主键
     * 算法id
     */
    private Long idAlgorithm;

    /**
     * 算法名称
     */
    private String name;

    /**
     * 算法描述
     */
    private String descript;

    /**
     * 1x 正常代码中写死 11 普通 2x sql嵌入 21, sql串 22. 函数 23. 存储过程 3x 接口嵌入 31. http接口 32. ws接口
     */
    private String sdAsses;

    /**
     * 评估组件嵌入代码
     */
    private String script;

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
