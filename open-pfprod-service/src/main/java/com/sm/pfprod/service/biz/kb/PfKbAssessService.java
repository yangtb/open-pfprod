package com.sm.pfprod.service.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.assess.*;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfKbAssessService
 * @Description: 评估组件用例
 * @Author yangtongbin
 * @Date 2018/10/22
 */
public interface PfKbAssessService {

    /**
     * 评估组件用例列表
     *
     * @param dto
     * @return
     */
    PageResult listKbAssess(PfEvaCaseDto dto);

    /**
     * 新增评估组件用例
     *
     * @param dto
     * @return
     */
    Long saveKbAssess(FaqEvaCase dto);

    /**
     * 删除评估组件用例
     *
     * @param dto
     * @return
     */
    boolean delKbAssess(PfBachChangeStatusDto dto);

    /**
     * 评估项_拟诊_列表
     *
     * @param dto
     * @return
     */
    PageResult listKbReferral(PfAssessCommonDto dto);

    /**
     * 拟诊 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemReferral> listReferral(PfAssessCommonDto dto);

    /**
     * 拟诊 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delReferral(PfBachChangeStatusDto dto);

    /**
     * 拟诊 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveReferral(PfAssessReferralDto dto);

    /**
     * 确诊 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbDiagnosis(PfAssessCommonDto dto);

    /**
     * 确诊 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemDiagnosis> listDiagnosisAnswer(PfAssessCommonDto dto);

    /**
     * 确诊 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delDiagnosis(PfBachChangeStatusDto dto);

    /**
     * 确诊 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveDiagnosis(PfAssessDiagnosisDto dto);

    /**
     * 确诊理由 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbReason(PfAssessCommonDto dto);

    /**
     * 确诊理由 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemReason> listReasonAnswer(PfAssessCommonDto dto);

    /**
     * 确诊理由 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delReason(PfBachChangeStatusDto dto);

    /**
     * 确诊理由 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveReason(PfAssessReasonDto dto);


    /**
     * 鉴定检查 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbCover(PfAssessCommonDto dto);

    /**
     * 鉴定检查 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemCover> listCoverAnswer(PfAssessCommonDto dto);

    /**
     * 鉴定检查 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delCover(PfBachChangeStatusDto dto);

    /**
     * 鉴定检查 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveCover(PfAssessCoverDto dto);


    /**
     * 覆盖必须检查 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbMust(PfAssessCommonDto dto);

    /**
     * 覆盖必须检查 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemMust> listMustAnswer(PfAssessCommonDto dto);

    /**
     * 覆盖必须检查 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delMust(PfBachChangeStatusDto dto);

    /**
     * 覆盖必须检查 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveMust(PfAssessMustDto dto);

    /**
     * 全面检查估表 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbThorough(PfAssessCommonDto dto);

    /**
     * 全面检查估表 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemThorough> listThoroughAnswer(PfAssessCommonDto dto);

    /**
     * 全面检查估表 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delThorough(PfBachChangeStatusDto dto);

    /**
     * 全面检查估表 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveThorough(PfAssessThoroughDto dto);

    /**
     * 检查效率 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbEffciency(PfAssessCommonDto dto);

    /**
     * 检查效率 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delEffciency(PfBachChangeStatusDto dto);

    /**
     * 检查效率 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveEffciency(PfAssessEffciencyDto dto);

    /**
     * 临时医嘱用药 - 评估项 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listKbOrder(PfAssessCommonDto dto);

    /**
     * 临时医嘱用药 - 等效答案列表
     *
     * @param dto
     * @return
     */
    List<FaqEvaCaseItemOrder> listOrderAnswer(PfAssessCommonDto dto);

    /**
     * 临时医嘱用药 - 删除等效答案
     *
     * @param dto
     * @return
     */
    boolean delOrder(PfBachChangeStatusDto dto);

    /**
     * 临时医嘱用药 - 保存等效答案
     *
     * @param dto
     * @return
     */
    Long saveOrder(PfAssessOrderDto dto);

    /**
     * 删除
     * @param dto
     * @return
     */
    boolean delCommonAssess(PfBachChangeStatusDto dto);

}
