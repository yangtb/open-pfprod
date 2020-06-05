package com.sm.pfprod.model.vo.dic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDicCache implements Serializable {

    private static final long serialVersionUID = -2591847411544699537L;

    /**
     * 显示名称
     */
    private String dictName;
    /**
     * 数据值编码
     */
    private String dictCode;
    /**
     * 扩展数据项
     */
    private String extValue;
    /**
     * 推荐排序，默认999，数值从小到大排序
     */
    private Integer sortNum;
    /**
     * 备注
     */
    private String remark;

}
