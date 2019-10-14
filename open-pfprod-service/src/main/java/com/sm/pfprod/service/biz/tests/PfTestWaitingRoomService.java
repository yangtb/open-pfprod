package com.sm.pfprod.service.biz.tests;

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

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: PfTestWaitingRoomService
 * @Description: 候诊室service
 * @Author yangtongbin
 * @Date 2018/11/10
 */
public interface PfTestWaitingRoomService {

    /**
     * 候诊室列表
     *
     * @param dto
     * @return
     */
    PageResult listWaitingRoom(PfTestWatingRoomDto dto);

    /**
     * 接诊列表
     *
     * @param dto
     * @return
     */
    PageResult listReceivePat(PfTestWatingRoomDto dto);

    /**
     * 获取试卷信息
     *
     * @param dto
     * @return
     */
    PfTestPaperVo selectTestPaperInfo(PfTestExamDto dto);

    /**
     * 获取试卷信息
     *
     * @param dto
     * @return
     */
    PfTestPaperVo selectTestPaper(PfTestExamDto dto);

    /**
     * 开始考试
     *
     * @param dto
     * @return
     */
    PfWaitingRoomStartVo startExam(ExmTestexec dto);

    /**
     * 交卷
     *
     * @param dto
     * @return
     */
    boolean endExam(ExmTestexec dto);

    /**
     * 查询患者信息
     *
     * @param dto
     * @return
     */
    PfWaitingRoomPatVo selectPatInfo(PfTestExamTagDto dto);

    /**
     * 查询问诊列表
     *
     * @param dto
     * @return
     */
    PageResult listTestCons(PfTestExamTagDto dto);

    /**
     * 问诊 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveConsQa(ExmMedResultInques dto);

    /**
     * 问诊 - 编辑问答问题
     *
     * @param dto
     * @return
     */
    boolean editConsQa(PfExmMedResultDto dto);

    /**
     * 问诊 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateConsStatus(PfBachChangeStatusDto dto);

    /**
     * 问诊 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomConsVo> listConsQa(PfTestExamTagDto dto);

    /**
     * 查询检查图片
     *
     * @param dto
     * @return
     */
    FaqMedCaseBody selectPic(PfTestExamTagDto dto);


    /**
     * 检查 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listTestCheck(PfTestExamTagDto dto);

    /**
     * 检查 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveCheckQa(ExmMedResultBody dto);

    /**
     * 检查 - 编辑问答问题
     *
     * @param dto
     * @return
     */
    boolean editCheckQa(ExmMedResultBody dto);

    /**
     * 检查 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateCheckStatus(PfBachChangeStatusDto dto);

    /**
     * 检查 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomCheckVo> listCheckQa(PfTestExamTagDto dto);

    /**
     * 检验 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listTestExam(PfTestExamTagDto dto);

    /**
     * 检验 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveExamQa(ExmMedResultInspect dto);

    /**
     * 检验 - 批量保存问答问题
     *
     * @param dto
     * @return
     */
    BigDecimal saveBatchExamQa(PfTestExamTagDto dto);

    /**
     * 检验 - 保存问答问题
     *
     * @param dto
     * @return
     */
    boolean editExamQa(ExmMedResultInspect dto);

    /**
     * 检验 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateExamStatus(PfBachChangeStatusDto dto);

    /**
     * 检验 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomExamVo> listExamQa(PfTestExamTagDto dto);

    /**
     * 拟诊 - 保存
     *
     * @param dto
     * @return
     */
    Long saveReferral(ExmMedResultReferral dto);

    /**
     * 已添加拟诊
     *
     * @param dto
     * @return
     */
    List<ExmMedResultReferral> listReferral(PfTestExamTagDto dto);

    /**
     * 医嘱 - 查询
     *
     * @param idTestexecResult 执行结果id
     * @return
     */
    ExmMedResultOrder selectOrders(Long idTestexecResult);

    /**
     * 医嘱 - 保存
     *
     * @param dto
     * @return
     */
    Long saveOrder(ExmMedResultOrder dto);

    /**
     * 医嘱 - 保存药品
     *
     * @param dto
     * @return
     */
    String saveDrugs(PfCommonListDto dto);

    /**
     * 医嘱 - 长期用药列表
     *
     * @param idTestexecResultOrder 执行结果id
     * @return
     */
    PageResult listLongDrugs(Long idTestexecResultOrder);

    /**
     * 医嘱 - 短期用药列表
     *
     * @param idTestexecResultOrder 执行结果id
     * @return
     */
    PageResult listShortDrugs(Long idTestexecResultOrder);

    /**
     * 删除用药
     *
     * @param type 类型
     * @param id   主键
     * @return
     */
    boolean delDrugs(String type, Long id);

    /**
     * 保存诊断
     *
     * @param dto
     * @return
     */
    Long saveDiagnosis(ExmMedResultDiagnosis dto);

    /**
     * 保存鉴别诊断
     *
     * @param dto
     * @return
     */
    Long saveIdentifyDiagnosis(ExmMedResultIdentify dto);

    /**
     * 删除诊断
     *
     * @param idTestexecResultDiagnosis 主键
     * @return
     */
    boolean delDiagnosis(Long idTestexecResultDiagnosis);

    /**
     * 保存诊断小结
     *
     * @param dto
     * @return
     */
    Long saveSummary(ExmMedResultSummary dto);

    /**
     * 保存确诊理由
     *
     * @param dto
     * @return
     */
    boolean saveDieReason(List<ExmMedResultDieReason> dto);

    /**
     * 删除确诊理由
     *
     * @param idDieReason 主键
     * @return
     */
    boolean delDieReason(Long idDieReason);


    /**
     * 查询诊断、确诊理由
     *
     * @param idTestexecResult 病例结果ID
     * @return
     */
    List<PfDiagnosisVo> selectAllDiagnosis(Long idTestexecResult);

    /**
     * 查询拟诊
     *
     * @param idTestexecResult 病例结果ID
     * @return
     */
    List<ExmMedResultReferral> selectAllReferral(Long idTestexecResult);

    /**
     * 查询诊断、诊断小结
     *
     * @param dto
     * @return
     */
    PfWaitingRoomDiagnosisVo selectDiagnosis(ExmMedResultDiagnosis dto);

    /**
     * 查询诊断小结
     *
     * @param idTestexecResult 病例结果ID
     * @return
     */
    ExmMedResultSummary selectSummary(Long idTestexecResult);

    /**
     * 查询已做问诊、检查、检验
     *
     * @return
     */
    List<PfWaitingRoomDieReasonVo> listReadyDieReason(Long idTestexecResult, String keyword);

    /**
     * 查询确诊理由
     *
     * @param idTestexecResultDiagnosis
     * @return
     */
    PageResult listDieReason(Long idTestexecResultDiagnosis);

    /**
     * 查询病例评估得分
     *
     * @param idTestexecResult
     * @param idMedicalrec
     * @return
     */
    List<PfEvaExecVo> selectScore(Long idTestexecResult, Long idMedicalrec);

    /**
     * 查询病例评估
     *
     * @param dto
     * @return
     */
    List<PfEvaExecVo> listEva(PfTestEvaDto dto);

    /**
     * 查询评估日志
     *
     * @param idTestexecResult
     * @return
     */
    List<ExmEvaLog> listEvaLog(Long idTestexecResult);

    /**
     * 病例评估
     *
     * @param idTestexecResult
     * @return
     */
    boolean medEva(Long idTestexecResult);

    /**
     * 修改得分
     *
     * @param dto
     * @return
     */
    boolean editEva(ExmEvaDimension dto);

    /**
     * 查询病例执行日志
     *
     * @param idTestexecResult
     * @return
     */
    List<PfExecLogVo> listExecLog(Long idTestexecResult);

    /**
     * 评估结果
     *
     * @param idTestexecResult
     * @return
     */
    ExmEvaResult selectEvaResult(Long idTestexecResult);

    /**
     * 保存拟诊原因
     *
     * @param list
     * @return
     */
    boolean saveReferralReason(List<ExmMedResultReferralReason> list);

    /**
     * 拟诊原因列表
     *
     * @param idTestexecResultReferral
     * @param fgExclude
     * @return
     */
    PageResult listReferralReason(Long idTestexecResultReferral, String fgExclude);

    /**
     * 删除计划
     *
     * @param dto
     * @return
     */
    boolean delPlanDetail(PfBachChangeStatusDto dto);

    /**
     * 保存执行序号
     *
     * @param dto
     * @return
     */
    boolean saveExecSerialNo(ExmTestexec dto);

    /**
     * 查询拟诊
     *
     * @param idTestexecResult 病例结果ID
     * @return
     */
    PageResult listAllReferralDie(Long idTestexecResult, String keywords);

    /**
     * 确诊项 及 排除拟诊项
     *
     * @param dto
     * @return
     */
    List<PfDiagnosticAnalysisVo> listDiagnosticAnalysis(PfTestEvaDto dto);


    /**
     * 查询病例诊断分析详情
     *
     * @param dto
     * @return
     */
    List<PfDiagnosticAnalysisDetailVo> listDiagnosticAnalysisDetail(PfTestEvaDto dto);

    /**
     * 疾病目录树
     *
     * @param dto
     * @return
     */
    List<PfDiseaseZtreeVo> listDiseaseCatalogueTree(PfCatalogueTreeDto dto);

    /**
     * 思维导图
     *
     * @param dto
     * @return
     */
    String selectReferralChartData(PfTestEvaDto dto);

    /**
     * 查询诊断分析、鉴别诊断列表
     *
     * @param dto
     * @return
     */
    PageResult listDiagnosticChart(PfTestExamTagDto dto);

    /**
     * 考试完成后所需跳转信息
     *
     * @param idTestplanDetail
     * @return
     */
    PfWaitingRoomFinishVo selectFinishExamInfo(Long idTestplanDetail);

    /**
     * 患者页签idMedCase
     *
     * @param idTestplanDetail
     * @return
     */
    Long selectAssessPatIdMedCase(Long idTestplanDetail);

}
