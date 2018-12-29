package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 临床模拟_评估结果
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmEvaResult implements Serializable {

    private static final long serialVersionUID = 1542642009326L;


    /**
     * 主键
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 加权分值
     */
    private BigDecimal scoreWeight;

    /**
     * 1 侥幸
     * 2 过早诊断但确诊正确
     * 3 合格但滥用检查
     * 4 良好
     * 5 优秀
     * 6 完美
     */
    private String sdTitle;

    /**字典
     *
     */
    private String sdTitleDic;

}
