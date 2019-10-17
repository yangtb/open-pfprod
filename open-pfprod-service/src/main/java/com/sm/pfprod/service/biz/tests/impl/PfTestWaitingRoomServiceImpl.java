package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.result.pf.biz.disease.BasDieResult;
import com.sm.open.core.facade.model.result.pf.biz.disease.PfDiseaseZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyListResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseInquesListResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseInspectListResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.eva.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestWaitingRoomClient;
import com.sm.pfprod.model.dto.biz.exam.PfExmMedResultDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestEvaDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.disease.PfDiseaseZtreeVo;
import com.sm.pfprod.model.vo.biz.test.*;
import com.sm.pfprod.model.vo.biz.test.eva.PfDiagnosticAnalysisDetailVo;
import com.sm.pfprod.model.vo.biz.test.eva.PfDiagnosticAnalysisVo;
import com.sm.pfprod.model.vo.biz.test.eva.PfEvaExecVo;
import com.sm.pfprod.model.vo.biz.test.eva.PfExecLogVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    public PfTestPaperVo selectTestPaper(PfTestExamDto dto) {
        CommonResult<PfTestPaperResult> result = testWaitingRoomClient.selectTestPaper(BeanUtil.convert(dto, PfTestExamParam.class));
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
    public boolean editConsQa(PfExmMedResultDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.editConsQa(BeanUtil.convert(dto, PfExmMedResultParam.class));
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
    public boolean editCheckQa(ExmMedResultBody dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.editCheckQa(BeanUtil.convert(dto, ExmMedResultBodyParam.class));
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
    public BigDecimal saveBatchExamQa(PfTestExamTagDto dto) {
        CommonResult<BigDecimal> result = testWaitingRoomClient.saveBatchExamQa(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editExamQa(ExmMedResultInspect dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.editExamQa(BeanUtil.convert(dto, ExmMedResultInspectParam.class));
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

    @Override
    public Long saveReferral(ExmMedResultReferral dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveReferral(BeanUtil.convert(dto, ExmMedResultReferralParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<ExmMedResultReferral> listReferral(PfTestExamTagDto dto) {
        CommonResult<List<ExmMedResultReferralResult>> result = testWaitingRoomClient.listReferral(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), ExmMedResultReferral.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public ExmMedResultOrder selectOrders(Long idTestexecResult) {
        CommonResult<ExmMedResultOrderResult> result = testWaitingRoomClient.selectOrders(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), ExmMedResultOrder.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveOrder(ExmMedResultOrder dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveOrder(BeanUtil.convert(dto, ExmMedResultOrderParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public String saveDrugs(PfCommonListDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.saveDrugs(BeanUtil.convert(dto, PfCommonListParam.class));
        if (result != null && result.getIsSuccess()) {
            return dto.getExtType();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listShortDrugs(Long idTestexecResultOrder) {
        PfPageResult<ExmMedResultOrderShortDrugs> result = testWaitingRoomClient.listShortDrugs(idTestexecResultOrder);
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PageResult listLongDrugs(Long idTestexecResultOrder) {
        PfPageResult<ExmMedResultOrderLogDrugsResult> result = testWaitingRoomClient.listLongDrugs(idTestexecResultOrder);
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delDrugs(String type, Long id) {
        CommonResult<Boolean> result = testWaitingRoomClient.delDrugs(type, id);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveDiagnosis(ExmMedResultDiagnosis dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveDiagnosis(BeanUtil.convert(dto, ExmMedResultDiagnosisParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveIdentifyDiagnosis(ExmMedResultIdentify dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveIdentifyDiagnosis(BeanUtil.convert(dto, ExmMedResultIdentifyParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDiagnosis(Long idTestexecResultDiagnosis) {
        CommonResult<Boolean> result = testWaitingRoomClient.delDiagnosis(idTestexecResultDiagnosis);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveSummary(ExmMedResultSummary dto) {
        CommonResult<Long> result = testWaitingRoomClient.saveSummary(BeanUtil.convert(dto, ExmMedResultSummaryParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveDieReason(List<ExmMedResultDieReason> dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.saveDieReason(BeanUtil.convertList(dto, ExmMedResultDieReasonParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDieReason(Long idDieReason) {
        CommonResult<Boolean> result = testWaitingRoomClient.delDieReason(idDieReason);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfDiagnosisVo> selectAllDiagnosis(Long idTestexecResult) {
        CommonResult<List<PfDiagnosisResult>> result = testWaitingRoomClient.selectAllDiagnosis(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return PfTestPaperBeanUtil.convertZdList(result.getContent());
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<ExmMedResultReferral> selectAllReferral(Long idTestexecResult) {
        CommonResult<List<ExmMedResultReferralResult>> result = testWaitingRoomClient.selectAllReferral(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), ExmMedResultReferral.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PfWaitingRoomDiagnosisVo selectDiagnosis(ExmMedResultDiagnosis dto) {
        CommonResult<PfWaitingRoomDiagnosisResult> result = testWaitingRoomClient.selectDiagnosis(BeanUtil.convert(dto, ExmMedResultDiagnosisParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfWaitingRoomDiagnosisVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public ExmMedResultSummary selectSummary(Long idTestexecResult) {
        CommonResult<ExmMedResultSummaryResult> result = testWaitingRoomClient.selectSummary(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), ExmMedResultSummary.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfWaitingRoomDieReasonVo> listReadyDieReason(Long idTestexecResult, String keyword) {
        CommonResult<List<PfWaitingRoomDieReasonResult>> result = testWaitingRoomClient.listReadyDieReason(idTestexecResult, keyword);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfWaitingRoomDieReasonVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listDieReason(Long idTestexecResultDiagnosis) {
        PfPageResult<PfWaitingRoomDieReasonResult> result = testWaitingRoomClient.listDieReason(idTestexecResultDiagnosis);
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<PfEvaExecVo> selectScore(Long idTestexecResult, Long idMedicalrec) {
        CommonResult<List<PfEvaExecResult>> result = testWaitingRoomClient.selectScore(idTestexecResult, idMedicalrec);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfEvaExecVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfEvaExecVo> listEva(PfTestEvaDto dto) {
        CommonResult<List<PfEvaExecResult>> result = testWaitingRoomClient.listEva(BeanUtil.convert(dto, PfTestEvaParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfEvaExecVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<ExmEvaLog> listEvaLog(Long idTestexecResult) {
        CommonResult<List<ExmEvaLogResult>> result = testWaitingRoomClient.listEvaLog(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), ExmEvaLog.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean medEva(Long idTestexecResult) {
        CommonResult<Boolean> result = testWaitingRoomClient.medEva(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editEva(ExmEvaDimension dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.editEva(BeanUtil.convert(dto, ExmEvaDimensionParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfExecLogVo> listExecLog(Long idTestexecResult) {
        CommonResult<List<PfExecLogResult>> result = testWaitingRoomClient.listExecLog(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfExecLogVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public ExmEvaResult selectEvaResult(Long idTestexecResult) {
        CommonResult<ExmEvaResultResult> result = testWaitingRoomClient.selectEvaResult(idTestexecResult);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), ExmEvaResult.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveReferralReason(List<ExmMedResultReferralReason> list) {
        CommonResult<Boolean> result = testWaitingRoomClient.saveReferralReason(BeanUtil.convertList(list, ExmMedResultReferralReasonParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listReferralReason(Long idTestexecResultReferral, String fgExclude) {
        PfPageResult<PfReferralReasonResult> result = testWaitingRoomClient.listReferralReason(idTestexecResultReferral);
        if (result == null) {
            return null;
        }
        result.setData(result.getData().stream().filter(
                pfReferralReasonResult -> pfReferralReasonResult.getFgExclude().equals(fgExclude))
                .collect(Collectors.toList()));
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delPlanDetail(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.delPlanDetail(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveExecSerialNo(ExmTestexec dto) {
        CommonResult<Boolean> result = testWaitingRoomClient.saveExecSerialNo(BeanUtil.convert(dto, ExmTestexecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listAllReferralDie(Long idTestexecResult, String keywords) {
        PfPageResult<BasDieResult> result = testWaitingRoomClient.listAllReferralDie(idTestexecResult, keywords);
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<PfDiagnosticAnalysisVo> listDiagnosticAnalysis(PfTestEvaDto dto) {
        CommonResult<List<PfDiagnosticAnalysisResult>> result = testWaitingRoomClient.listDiagnosticAnalysis(BeanUtil.convert(dto, PfTestEvaParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfDiagnosticAnalysisVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfDiagnosticAnalysisDetailVo> listDiagnosticAnalysisDetail(PfTestEvaDto dto) {
        CommonResult<List<PfDiagnosticAnalysisDetailResult>> result = testWaitingRoomClient.listDiagnosticAnalysisDetail(BeanUtil.convert(dto, PfTestEvaParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfDiagnosticAnalysisDetailVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfDiseaseZtreeVo> listDiseaseCatalogueTree(PfCatalogueTreeDto dto) {
        CommonResult<List<PfDiseaseZtreeResult>> result = testWaitingRoomClient.listDiseaseCatalogueTree(
                BeanUtil.convert(dto, PfCatalogueTreeParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfDiseaseZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public String selectReferralChartData(PfTestEvaDto dto) {
        CommonResult<String> result = testWaitingRoomClient.selectReferralChartData(BeanUtil.convert(dto, PfTestEvaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listDiagnosticChart(PfTestExamTagDto dto) {
        PfPageResult<BasDieResult> result = testWaitingRoomClient.listDiagnosticChart(BeanUtil.convert(dto, PfTestExamTagParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PfWaitingRoomFinishVo selectFinishExamInfo(Long idTestplanDetail) {
        CommonResult<PfWaitingRoomFinishResult> result = testWaitingRoomClient.selectFinishExamInfo(idTestplanDetail);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfWaitingRoomFinishVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long selectAssessPatIdMedCase(Long idTestplanDetail) {
        CommonResult<Long> result = testWaitingRoomClient.selectAssessPatIdMedCase(idTestplanDetail);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public String selectEvaGuideContent(Long idTestplanDetail) {
        CommonResult<String> result = testWaitingRoomClient.selectEvaGuideContent(idTestplanDetail);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}
