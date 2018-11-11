package com.sm.pfprod.model.vo.biz.test.paper;

import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfTestPaperVo implements Serializable {

    private static final long serialVersionUID = -8593324346365792342L;

    /**
     * 学生信息
     */
    private PfTestPaperStudentVo studentInfo;

    /**
     * 试卷信息
     */
    private PfTestPaperInfoVo paperInfo;

    /**
     * 标签
     */
    List<PfCaseHistoryTagVo> tags;
}
