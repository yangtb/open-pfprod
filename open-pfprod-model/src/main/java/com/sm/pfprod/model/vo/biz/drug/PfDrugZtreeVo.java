package com.sm.pfprod.model.vo.biz.drug;

import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDrugZtreeVo extends PfCommonZtreeVo implements Serializable {

    private static final long serialVersionUID = -4901737093643963963L;

    /**
     * 药品目录ID
     */
    private Long idDrugsclass;

    /**
     * 激活标志
     */
    private String fgActive;

    /**
     * 是否是父节点
     */
    //@JSONField(name = "isParent")
    //private boolean isParent;
}
