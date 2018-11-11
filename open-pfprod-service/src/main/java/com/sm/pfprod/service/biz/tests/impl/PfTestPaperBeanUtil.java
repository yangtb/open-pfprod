package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.care.core.utils.DateUtil;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperInfoVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperStudentVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;

public class PfTestPaperBeanUtil {

    /**
     * bean转换
     *
     * @param result
     * @return
     */
    public static PfTestPaperVo convert(PfTestPaperResult result) {
        if (result == null) {
            return null;
        }
        PfTestPaperVo testPaperVo = new PfTestPaperVo();
        testPaperVo.setStudentInfo(BeanUtil.convert(result.getStudentInfo(), PfTestPaperStudentVo.class));
        testPaperVo.setPaperInfo(BeanUtil.convert(result.getPaperInfo(), PfTestPaperInfoVo.class));
        testPaperVo.setTags(BeanUtil.convertList(result.getTags(), PfCaseHistoryTagVo.class));

        if (testPaperVo.getPaperInfo().getBeginTime() != null) {
            testPaperVo.getPaperInfo().setBeginTimeStr(DateUtil.formateDate(testPaperVo.getPaperInfo().getBeginTime(), DateUtil.DEFAULT_FORMAT));
        }
        if (testPaperVo.getPaperInfo().getEndTime() != null) {
            testPaperVo.getPaperInfo().setEndTimeStr(DateUtil.formateDate(testPaperVo.getPaperInfo().getEndTime(), DateUtil.DEFAULT_FORMAT));
        }

        return testPaperVo;
    }
}
