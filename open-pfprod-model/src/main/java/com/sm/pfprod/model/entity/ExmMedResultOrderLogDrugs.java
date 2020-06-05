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
 * 临床模拟_测试执行_病例结果_医嘱_长期用药
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultOrderLogDrugs implements Serializable {

    private static final long serialVersionUID = 1542159815889L;

    /**
     * 主键
     */
    private Long idOrderLongDrugs;

    /**
     * 病例结果医嘱ID
     */
    private Long idTestexecResultOrder;

    /**
     * 长期用药
     */
    private Long idLongDrugs;

    /**
     * 长期用药txt
     */
    private String idLongDrugsText;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;


}
