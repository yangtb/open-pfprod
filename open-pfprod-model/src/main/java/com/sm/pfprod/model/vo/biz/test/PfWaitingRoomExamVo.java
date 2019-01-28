package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomExamVo implements Serializable {

    /**
     * 临床模拟_测试执行_病例结果_检验id
     */
    private Long idTestexecResultInspect;


    /**
     * 线索标记
     */
    private String fgClue;

    /**
     * 检查项
     */
    private String naItem;

    /**
     * 检查结果id
     */
    private Long idResult;

    /**
     * 答案
     */
    private String valResult;

    /**
     * 标准值
     */
    private String desStand;

    /**
     * 专家解读
     */
    private String desExpert;

    /**
     * 1 图片 2 音频 3 视频 4 其它
     */
    private String sdType;

    /**
     * 文件路径
     */
    private String path;

}
