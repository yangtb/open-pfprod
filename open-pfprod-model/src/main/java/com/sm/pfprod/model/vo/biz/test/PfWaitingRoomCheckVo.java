package com.sm.pfprod.model.vo.biz.test;

import com.sm.pfprod.model.entity.BasMedia;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfWaitingRoomCheckVo implements Serializable {

    /**
     * 临床模拟_测试执行_病例结果_检查id
     */
    private Long idTestexecResultBody;

    /**
     * 线索标记
     */
    private String fgClue;

    /**
     * 检查项
     */
    private String desBody;

    /**
     * 检查结果id
     */
    private Long idResult;

    /**
     * 答案
     */
    private String desResult;

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

    /**
     * 多媒体id
     */
    private String idMedia;

    /**
     * 多媒体
     */
    private List<BasMedia> mediaList;
}
