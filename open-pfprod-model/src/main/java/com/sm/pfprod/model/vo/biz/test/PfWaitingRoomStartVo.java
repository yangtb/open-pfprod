package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomStartVo implements Serializable {

    /**
     * 病例执行id
     */
    private Long idTestexec;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

}
