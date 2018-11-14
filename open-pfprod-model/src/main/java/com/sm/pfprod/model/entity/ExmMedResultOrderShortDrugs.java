package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 临床模拟_测试执行_病历结果_医嘱_临时用药
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultOrderShortDrugs implements Serializable {

    private static final long serialVersionUID = 1542159819705L;


    /**
     * 主键
     */
    private Long idOrderShortDrugs;

    /**
     * 病历结果医嘱ID
     */
    private Long idTestexecResultOrder;

    /**
     * 临时用药
     */
    private Long idShortDrugs;

    /**
     * 临时用药txt
     */
    private String idShortDrugsText;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

}
