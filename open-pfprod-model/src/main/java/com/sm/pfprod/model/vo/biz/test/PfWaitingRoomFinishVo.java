package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomFinishVo implements Serializable {

    private static final long serialVersionUID = -3489729407748507766L;


    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 学生id
     */
    private Long idStudent;

    /**
     * 试卷ID
     */
    private Long idTestpaper;

    /**
     * 病例用途 : 1 训练病例 2 测试病例
     */
    private String sdUse;

}
