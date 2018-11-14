package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestWaitingRoomFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TestWaitingRoomClient {

    @Resource
    private PfTestWaitingRoomFacade pfTestWaitingRoomFacade;

    public PfPageResult listWaitingRoom(PfTestWatingRoomParam param) {
        return pfTestWaitingRoomFacade.listWaitingRoom(param);
    }

    public PfPageResult listReceivePat(PfTestWatingRoomParam param) {
        return pfTestWaitingRoomFacade.listReceivePat(param);
    }

    public CommonResult<PfTestPaperResult> selectTestPaperInfo(PfTestExamParam param) {
        return pfTestWaitingRoomFacade.selectTestPaperInfo(param);
    }

    public CommonResult<PfWaitingRoomStartResult> startExam(ExmTestexecParam param) {
        return pfTestWaitingRoomFacade.startExam(param);
    }

    public CommonResult<Boolean> endExam(ExmTestexecParam param) {
        return pfTestWaitingRoomFacade.endExam(param);
    }

    public CommonResult<PfWaitingRoomPatResult> selectPatInfo(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.selectPatInfo(param);
    }

    public PfPageResult listTestCons(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listTestCons(param);
    }

    public CommonResult<Long> saveConsQa(ExmMedResultInquesParam param) {
        return pfTestWaitingRoomFacade.saveConsQa(param);
    }

    public CommonResult<Boolean> updateConsStatus(PfBachChangeStatusParam param) {
        return pfTestWaitingRoomFacade.updateConsStatus(param);
    }

    public CommonResult<List<PfWaitingRoomConsResult>> listConsQa(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listConsQa(param);
    }

    public CommonResult<FaqMedCaseBodyResult> selectPic(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.selectPic(param);
    }

    public PfPageResult listTestCheck(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listTestCheck(param);
    }

    public CommonResult<Long> saveCheckQa(ExmMedResultBodyParam param) {
        return pfTestWaitingRoomFacade.saveCheckQa(param);
    }

    public CommonResult<Boolean> updateCheckStatus(PfBachChangeStatusParam param) {
        return pfTestWaitingRoomFacade.updateCheckStatus(param);
    }

    public CommonResult<List<PfWaitingRoomCheckResult>> listCheckQa(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listCheckQa(param);
    }


    public PfPageResult listTestExam(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listTestExam(param);
    }


    public CommonResult<Long> saveExamQa(ExmMedResultInspectParam param) {
        return pfTestWaitingRoomFacade.saveExamQa(param);
    }

    public CommonResult<Boolean> updateExamStatus(PfBachChangeStatusParam param) {
        return pfTestWaitingRoomFacade.updateExamStatus(param);
    }

    public CommonResult<List<PfWaitingRoomExamResult>> listExamQa(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listExamQa(param);
    }

    public CommonResult<Long> saveReferral(ExmMedResultReferralParam param) {
        return pfTestWaitingRoomFacade.saveReferral(param);
    }

    public CommonResult<List<ExmMedResultReferralResult>> listReferral(PfTestExamTagParam param) {
        return pfTestWaitingRoomFacade.listReferral(param);
    }

    public CommonResult<ExmMedResultOrderResult> selectOrders(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.selectOrders(idTestexecResult);
    }

    public CommonResult<Long> saveOrder(ExmMedResultOrderParam param) {
        return pfTestWaitingRoomFacade.saveOrder(param);
    }

    public CommonResult<Boolean> saveDrugs(PfCommonListParam param) {
        return pfTestWaitingRoomFacade.saveDrugs(param);
    }

    public PfPageResult listLongDrugs(Long idTestexecResultOrder) {
        return pfTestWaitingRoomFacade.listLongDrugs(idTestexecResultOrder);
    }

    public PfPageResult listShortDrugs(Long idTestexecResultOrder) {
        return pfTestWaitingRoomFacade.listShortDrugs(idTestexecResultOrder);
    }

    public CommonResult<Boolean> delDrugs(String type, Long id) {
        return pfTestWaitingRoomFacade.delDrugs(type, id);
    }

}
