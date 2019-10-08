package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomConsVo implements Serializable {

    /**
     * 临床模拟_测试执行_病例结果_问诊id
     */
    private Long idTestexecResultInques;


    /**
     * 线索标记
     */
    private String fgClue;

    /**
     * 问题
     */
    private String desInques;

    /**
     * 答案id
     */
    private Long idAnswer;

    /**
     * 答案
     */
    private String desAnswer;

    /**
     * 是否阳性
     */
    private String isMasculine;

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

    /**
     * 是否需要说明理由
     */
    private String fgReason;

    /**
     * 是否根据病人回答反馈
     */
    private String fgBack;

    /**
     * 说明理由
     */
    private String desReason;

    /**
     * 解释患者的回复
     */
    private String desReply;
}
