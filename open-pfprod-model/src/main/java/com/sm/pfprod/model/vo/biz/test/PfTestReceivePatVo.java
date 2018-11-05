package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestReceivePatVo implements Serializable {

    private static final long serialVersionUID = 7805306448357333809L;

    /**
     * 接诊状态
     */
    private String status;

    /**
     * 接诊时间
     */
    private String receiveDate;

    /**
     * 接诊耗时(m)
     */
    private Integer receiveConsumingTime;

    /**
     * 患者姓名
     */
    private String patName;
    /**
     * 性别
     */
    private String patSex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 病例
     */
    private String receiveDoc;

    /**
     * 接诊医师
     */
    private String medicalrecName;

    /**
     * 测试计划
     */
    private String naTestplan;

    /**
     * 试卷名称
     */
    private String naTestpaper;

    /**
     * 评分
     */
    private String score;

    /**
     * 称号
     */
    private String ch;

    /**
     * 评估老师
     */
    private String AssessTeacher;

    /**
     * 评估日期
     */
    private String AssessDate;


}
