package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestWatingRoomVo implements Serializable {

    private static final long serialVersionUID = 7805306448357333809L;

    /**
     * 接诊状态
     */
    private String status;
    /**
     * 患者姓名
     */
    private String patName;
    /**
     * 性别
     */
    private String patSex;
    /**
     * 分配医师
     */
    private String distributeDoc;

    /**
     * 测试计划
     */
    private String naTestplan;

    /**
     * 试卷名称
     */
    private String naTestpaper;

}
