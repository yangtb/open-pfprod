package com.sm.pfprod.model.vo.biz.disease;

import com.alibaba.fastjson.annotation.JSONField;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDiseaseZtreeVo extends PfCommonZtreeVo implements Serializable {

    private static final long serialVersionUID = -4901737093643963963L;

    /**
     * 疾病目录ID
     */
    private Long idDieclass;

    /**
     * 疾病目录编码
     */
    private String cdDieclass;

    /**
     * 疾病id
     */
    private Long idDie;

    /**
     * 激活标志
     */
    private String fgActive;

    /**
     * 是否是父节点
     */
    @JSONField(name = "isParent")
    private boolean isParent;
}
