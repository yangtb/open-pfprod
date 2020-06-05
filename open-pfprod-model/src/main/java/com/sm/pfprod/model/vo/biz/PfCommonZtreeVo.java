package com.sm.pfprod.model.vo.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfCommonZtreeVo implements Serializable {

    private static final long serialVersionUID = -4901737093643963963L;

    /**
     * 目录编码
     */
    private String id;
    /**
     * 上级编码
     */
    private String pId;
    /**
     * 疾病目录名称
     */
    private String name;
    /**
     * 节点打开状态
     */
    private boolean open;
    /**
     * 不显示复选框
     */
    private boolean nocheck;
    /**
     * 是否选中
     */
    private boolean checked;
    /**
     * true 无右键菜单
     */
    private boolean noR;
    /**
     * 扩展字段
     */
    private String ext;
}
