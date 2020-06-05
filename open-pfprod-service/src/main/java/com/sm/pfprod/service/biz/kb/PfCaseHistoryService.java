package com.sm.pfprod.service.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.casehistory.PfCaseHistoryDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaTag;
import com.sm.pfprod.model.entity.FaqMedTag;
import com.sm.pfprod.model.entity.FaqMedicalrec;
import com.sm.pfprod.model.entity.FaqMedicalrecCa;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.model.vo.biz.clinic.PfAssessTagVo;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;

import java.util.List;

/**
 * @ClassName: PfCaseHistoryService
 * @Description: 病例service
 * @Author yangtongbin
 * @Date 2018/10/10
 */
public interface PfCaseHistoryService {

    /**
     * 分类树
     *
     * @return
     */
    List<PfCommonZtreeVo> listClassifyTree();

    /**
     * 新增分类信息
     *
     * @param dto
     * @return
     */
    Long addClassify(FaqMedicalrecCa dto);

    /**
     * 编辑分类信息
     *
     * @param dto
     * @return
     */
    boolean editClassify(FaqMedicalrecCa dto);

    /**
     * 删除分类信息
     *
     * @param dto
     * @return
     */
    boolean delClassify(PfBachChangeStatusDto dto);

    /**
     * 模板列表
     *
     * @param dto
     * @return
     */
    PageResult listTemplate(PfCaseHistoryDto dto);

    /**
     * 新增模板
     *
     * @param dto
     * @return
     */
    boolean addTemplate(FaqMedicalrec dto);

    /**
     * 编辑模板
     *
     * @param dto
     * @return
     */
    boolean editTemplate(FaqMedicalrec dto);

    /**
     * 删除模板
     *
     * @param dto
     * @return
     */
    boolean delTemplate(PfBachChangeStatusDto dto);

    /**
     * 保存病例标签
     *
     * @param dto
     * @return
     */
    Long saveMedTag(FaqMedTag dto);


    /**
     * 保存评估标签
     *
     * @param dto
     * @return
     */
    Long saveEvaTag(FaqEvaTag dto);

    /**
     * 所有病例标签
     *
     * @param idDemo
     * @param idMedicalrec
     * @return
     */
    List<PfCaseHistoryTagVo> listAllCaseHistoryTag(Long idDemo, Long idMedicalrec);

    /**
     * 所有评估表
     *
     * @param idDemo
     * @param idMedicalrec
     * @return
     */
    List<PfAssessTagVo> listAllAssessTag(Long idDemo, Long idMedicalrec);

    /**
     * 查询病例标签信息
     *
     * @param dto
     * @return
     */
    FaqMedTag selectMedTag(FaqMedTag dto);

    /**
     * 查询评估标签信息
     *
     * @param dto
     * @return
     */
    FaqEvaTag selectEvaTag(FaqEvaTag dto);

    /**
     * 重载病例组件
     *
     * @param dto
     * @return
     */
    boolean saveAsMed(FaqMedTag dto);

    /**
     * 重载评估组件
     *
     * @param dto
     * @return
     */
    boolean saveAsEva(FaqEvaTag dto);
}
