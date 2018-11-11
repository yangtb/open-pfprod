package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.PfWaitingRoomConsResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.PfWaitingRoomPatResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.PfWaitingRoomStartResult;
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

}
