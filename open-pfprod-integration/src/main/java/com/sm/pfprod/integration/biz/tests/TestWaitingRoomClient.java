package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.result.pf.biz.disease.PfDiseaseZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.eva.*;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.paper.PfTestPaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestWaitingRoomFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    public CommonResult<PfTestPaperResult> selectTestPaper(PfTestExamParam param) {
        return pfTestWaitingRoomFacade.selectTestPaper(param);
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

    public CommonResult<Boolean> editConsQa(PfExmMedResultParam param) {
        return pfTestWaitingRoomFacade.editConsQa(param);
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

    public CommonResult<Boolean> editCheckQa(ExmMedResultBodyParam param) {
        return pfTestWaitingRoomFacade.editCheckQa(param);
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

    public CommonResult<BigDecimal> saveBatchExamQa(List<ExmMedResultInspectParam> param) {
        return pfTestWaitingRoomFacade.saveBatchExamQa(param);
    }

    public CommonResult<Boolean> editExamQa(ExmMedResultInspectParam param) {
        return pfTestWaitingRoomFacade.editExamQa(param);
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

    public CommonResult<Long> saveDiagnosis(ExmMedResultDiagnosisParam param) {
        return pfTestWaitingRoomFacade.saveDiagnosis(param);
    }

    public CommonResult<Long> saveIdentifyDiagnosis(ExmMedResultIdentifyParam param) {
        return pfTestWaitingRoomFacade.saveIdentifyDiagnosis(param);
    }

    public CommonResult<Boolean> delDiagnosis(Long idTestexecResultDiagnosis) {
        return pfTestWaitingRoomFacade.delDiagnosis(idTestexecResultDiagnosis);
    }

    public CommonResult<Long> saveSummary(ExmMedResultSummaryParam param) {
        return pfTestWaitingRoomFacade.saveSummary(param);
    }

    public CommonResult<Boolean> saveDieReason(List<ExmMedResultDieReasonParam> param) {
        return pfTestWaitingRoomFacade.saveDieReason(param);
    }

    public CommonResult<Boolean> delDieReason(Long idDieReason) {
        return pfTestWaitingRoomFacade.delDieReason(idDieReason);
    }

    public CommonResult<List<PfDiagnosisResult>> selectAllDiagnosis(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.selectAllDiagnosis(idTestexecResult);
    }

    public CommonResult<List<ExmMedResultReferralResult>> selectAllReferral(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.selectAllReferral(idTestexecResult);
    }

    public CommonResult<PfWaitingRoomDiagnosisResult> selectDiagnosis(ExmMedResultDiagnosisParam param) {
        return pfTestWaitingRoomFacade.selectDiagnosis(param);
    }

    public CommonResult<ExmMedResultSummaryResult> selectSummary(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.selectSummary(idTestexecResult);
    }

    public CommonResult<List<PfWaitingRoomDieReasonResult>> listReadyDieReason(Long idTestexecResult, String keyword) {
        return pfTestWaitingRoomFacade.listReadyDieReason(idTestexecResult, keyword);
    }

    public PfPageResult listDieReason(Long idTestexecResultDiagnosis) {
        return pfTestWaitingRoomFacade.listDieReason(idTestexecResultDiagnosis);
    }

    public CommonResult<List<PfEvaExecResult>> listEva(PfTestEvaParam param) {
        return pfTestWaitingRoomFacade.listEva(param);
    }

    public CommonResult<List<PfEvaExecResult>> selectScore(Long idTestexecResult, Long idMedicalrec) {
        return pfTestWaitingRoomFacade.selectScore(idTestexecResult, idMedicalrec);
    }

    public CommonResult<List<ExmEvaLogResult>> listEvaLog(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.listEvaLog(idTestexecResult);
    }

    public CommonResult<Boolean> medEva(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.medEva(idTestexecResult);
    }

    public CommonResult<Boolean> editEva(ExmEvaDimensionParam param) {
        return pfTestWaitingRoomFacade.editEva(param);
    }

    public CommonResult<List<PfExecLogResult>> listExecLog(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.listExecLog(idTestexecResult);
    }

    public CommonResult<ExmEvaResultResult> selectEvaResult(Long idTestexecResult) {
        return pfTestWaitingRoomFacade.selectEvaResult(idTestexecResult);
    }

    public CommonResult<Boolean> saveReferralReason(List<ExmMedResultReferralReasonParam> list){
        return pfTestWaitingRoomFacade.saveReferralReason(list);
    }

   public PfPageResult listReferralReason(Long idTestexecResultReferral){
       return pfTestWaitingRoomFacade.listReferralReason(idTestexecResultReferral);
   }

    public CommonResult<Boolean> delPlanDetail(PfBachChangeStatusParam param) {
        return pfTestWaitingRoomFacade.delPlanDetail(param);
    }

    public CommonResult<Boolean> saveExecSerialNo(ExmTestexecParam param) {
        return pfTestWaitingRoomFacade.saveExecSerialNo(param);
    }

    public PfPageResult listAllReferralDie(Long idTestexecResult, String keywords) {
        return pfTestWaitingRoomFacade.listAllReferralDie(idTestexecResult, keywords);
    }

    public CommonResult<List<PfDiagnosticAnalysisResult>> listDiagnosticAnalysis(PfTestEvaParam param) {
        return pfTestWaitingRoomFacade.listDiagnosticAnalysis(param);
    }

    public CommonResult<List<PfDiagnosticAnalysisDetailResult>> listDiagnosticAnalysisDetail(PfTestEvaParam param) {
        return pfTestWaitingRoomFacade.listDiagnosticAnalysisDetail(param);
    }

    public CommonResult<List<PfDiseaseZtreeResult>> listDiseaseCatalogueTree(PfCatalogueTreeParam param) {
        return pfTestWaitingRoomFacade.listDiseaseCatalogueTree(param);
    }

    public CommonResult<String> selectReferralChartData(PfTestEvaParam param) {
        return pfTestWaitingRoomFacade.selectReferralChartData(param);
    }

    public PfPageResult listDiagnosticChart(PfTestExamTagParam param)  {
        return pfTestWaitingRoomFacade.listDiagnosticChart(param);
    }

    public CommonResult<PfWaitingRoomFinishResult> selectFinishExamInfo(Long idTestplanDetail) {
        return pfTestWaitingRoomFacade.selectFinishExamInfo(idTestplanDetail);
    }

    public CommonResult<Long> selectAssessPatIdMedCase(Long idTestplanDetail) {
        return pfTestWaitingRoomFacade.selectAssessPatIdMedCase(idTestplanDetail);
    }

    public CommonResult<String> selectEvaGuideContent(Long idTestplanDetail) {
        return pfTestWaitingRoomFacade.selectEvaGuideContent(idTestplanDetail);
    }


}
