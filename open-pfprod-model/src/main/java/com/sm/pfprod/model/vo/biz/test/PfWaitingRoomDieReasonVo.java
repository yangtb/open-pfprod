package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomDieReasonVo implements Serializable {

    /**
     * 主键
     */
    private Long idDieReason;

    /**
     * 确诊理由阶段
     */
    private String sdEvaEffciency;

    /**
     * 阶段对应问诊、检查、检验ID
     */
    private Long id;

    /**
     * 阶段对应问诊、检查、检验名称
     */
    private String idText;


}
