package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.care.core.utils.DateUtil;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.PfDiagnosisResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;
import com.sm.pfprod.model.vo.biz.test.PfDiagnosisVo;
import com.sm.pfprod.model.vo.biz.test.PfWaitingRoomDieReasonVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperInfoVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperStudentVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<PfCaseHistoryTagVo> tags = testPaperVo.getTags();
        tags = tags.stream().sorted(Comparator.comparing(PfCaseHistoryTagVo::getProcessSerialno)).collect(Collectors.toList());

        LinkedList<String> linkedList = new LinkedList<>();
        // 串行路径 组件编码-串行标识-序号
        for (PfCaseHistoryTagVo pfCaseHistoryTagVo : tags) {
            if (pfCaseHistoryTagVo.getSdProcess().equals("2")) {
                linkedList.add(pfCaseHistoryTagVo.getCdMedAsse() + "-" + pfCaseHistoryTagVo.getProcessSerialno());
            }
        }
        testPaperVo.setLink(linkedList);
        return testPaperVo;
    }

    public static List<PfDiagnosisVo> convertZdList(List<PfDiagnosisResult> result) {
        if (result == null) {
            return null;
        }
        List<PfDiagnosisVo> pfDiagnosisVos = new ArrayList<>(result.size());
        PfDiagnosisVo pfDiagnosisVo;
        for (PfDiagnosisResult pfDiagnosisResult : result) {
            pfDiagnosisVo = BeanUtil.convert(pfDiagnosisResult, PfDiagnosisVo.class);
            pfDiagnosisVo.setIdeReasonList(BeanUtil.convertList(pfDiagnosisResult.getIdeReasonList(), PfWaitingRoomDieReasonVo.class));
            pfDiagnosisVos.add(pfDiagnosisVo);
        }
        return pfDiagnosisVos;
    }


}
