package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyListResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseInquesListResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseInspectListResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestWaitingRoomClient;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.test.*;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfTestWaitingRoomService")
public class PfTestWaitingRoomServiceImpl implements PfTestWaitingRoomService {

    @Resource
    private TestWaitingRoomClient testWaitingRoomClient;

    @Override
    public PageResult listWaitingRoom(PfTestWatingRoomDto dto) {
        PfPageResult<PfTestWaitingRoomResult> result = testWaitingRoomClient.listWaitingRoom(BeanUtil.convert(dto, PfTestWatingRoomParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PageResult listReceivePat(PfTestWatingRoomDto dto) {
        PfPageResult<PfTestReceivePatResult> result = testWaitingRoomClient.listReceivePat(BeanUtil.convert(dto, PfTestWatingRoomParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PfTestPaperVo selectTestPaperInfo(PfTestExamDto dto) {
        CommonResult<PfTestPaperResult> result = testWaitingRoomClient.selectTestPaperInfo(BeanUtil.convert(dto, PfTestExamParam.class));
        if (result != null && result.getIsSuccess()) {
            return PfTestPaperBeanUtil.convert(result.getContent());
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PfWaitingRoomStartVo startExam(ExmTestexec dto) {
        CommonResult<PfWaitingRoomStartResult> result = testWaitingRoomClient.startExam(BeanUtil.convert(dto, ExmTestexecParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfWaitingRoomStartVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean endExam(ExmTestexec dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.endExam(BeanUtil.convert(dto, ExmTestexecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PfWaitingRoomPatVo selectPatInfo(PfTestExamTagDto dto) {
        CommonResult<PfWaitingRoomPatResult> result = testWaitingRoomClient.selectPatInfo(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfWaitingRoomPatVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTestCons(PfTestExamTagDto dto) {
        PfPageResult<FaqMedCaseInquesListResult> result = testWaitingRoomClient.listTestCons(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveConsQa(ExmMedResultInques dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveConsQa(BeanUtil.convert(dto, ExmMedResultInquesParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updateConsStatus(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.updateConsStatus(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfWaitingRoomConsVo> listConsQa(PfTestExamTagDto dto) {
        CommonResult<List<PfWaitingRoomConsResult>> result = testWaitingRoomClient.listConsQa(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfWaitingRoomConsVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseBody selectPic(PfTestExamTagDto dto) {
        CommonResult<FaqMedCaseBodyResult> result = testWaitingRoomClient.selectPic(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseBody.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTestCheck(PfTestExamTagDto dto) {
        PfPageResult<FaqMedCaseBodyListResult> result = testWaitingRoomClient.listTestCheck(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveCheckQa(ExmMedResultBody dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveCheckQa(BeanUtil.convert(dto, ExmMedResultBodyParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updateCheckStatus(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.updateCheckStatus(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfWaitingRoomCheckVo> listCheckQa(PfTestExamTagDto dto) {
        CommonResult<List<PfWaitingRoomCheckResult>> result = testWaitingRoomClient.listCheckQa(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfWaitingRoomCheckVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTestExam(PfTestExamTagDto dto) {
        PfPageResult<FaqMedCaseInspectListResult> result = testWaitingRoomClient.listTestExam(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveExamQa(ExmMedResultInspect dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveExamQa(BeanUtil.convert(dto, ExmMedResultInspectParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updateExamStatus(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.updateExamStatus(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfWaitingRoomExamVo> listExamQa(PfTestExamTagDto dto) {
        CommonResult<List<PfWaitingRoomExamResult>> result = testWaitingRoomClient.listExamQa(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfWaitingRoomExamVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
