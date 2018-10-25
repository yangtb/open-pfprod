package com.sm.pfprod.service.biz.clinic;

import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.model.vo.biz.clinic.PfAssessTagVo;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;

import java.util.List;

/**
 * @ClassName: PfClinicTemplateService
 * @Description: 临床模板定义
 * @Author yangtongbin
 * @Date 2018/10/8
 */
public interface PfClinicTemplateService {

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
    Long addClassify(BasDemoCa dto);

    /**
     * 编辑分类信息
     *
     * @param dto
     * @return
     */
    boolean editClassify(BasDemoCa dto);

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
    PageResult listTemplate(PfClinicTemplateDto dto);

    /**
     * 新增模板
     *
     * @param dto
     * @return
     */
    boolean addTemplate(BasDemo dto);

    /**
     * 编辑模板
     *
     * @param dto
     * @return
     */
    boolean editTemplate(BasDemo dto);

    /**
     * 删除模板
     *
     * @param dto
     * @return
     */
    boolean delTemplate(PfBachChangeStatusDto dto);

    /**
     * 病例标签列表
     *
     * @param dto
     * @return
     */
    PageResult listCaseHistoryTag(PfClinicTemplateDto dto);

    /**
     * 删除病例标签信息
     *
     * @param dto
     * @return
     */
    boolean delCaseHistoryTag(PfBachChangeStatusDto dto);

    /**
     * 保存病例标签信息
     *
     * @param dto
     * @return
     */
    Long saveCaseHistoryTag(BasMedicalTag dto);

    /**
     * 评估标签列表
     *
     * @param dto
     * @return
     */
    PageResult listSheetTag(PfClinicTemplateDto dto);

    /**
     * 删除评估标签信息
     *
     * @param dto
     * @return
     */
    boolean delSheetTag(PfBachChangeStatusDto dto);

    /**
     * 保存评估标签信息
     *
     * @param dto
     * @return
     */
    Long saveSheetTag(BasEvaTag dto);

    /**
     * 查询所有病例模板
     *
     * @return
     */
    List<BasDemo> listAllBasDemo();

    /**
     * 根据idDemo查询模板标签
     *
     * @param idDemo
     * @return
     */
    List<BasDemoTag> listTagByIdDemo(Long idDemo);

    /**
     * 评估维度树
     *
     * @return
     */
    List<PfCommonZtreeVo> listDimensionTree();

    /**
     * 删除评估维度
     *
     * @param dto
     * @return
     */
    boolean delDimensionTag(PfBachChangeStatusDto dto);

    /**
     * 保存评估维度
     *
     * @param dto
     * @return
     */
    Long saveDimensionTag(BasDemoAsses dto);

    /**
     * 根据id查询评估维度信息
     *
     * @param idDimemsion
     * @return
     */
    BasDemoAsses selectDimensionTagInfo(Long idDimemsion);

    /**
     * 所有病历标签
     *
     * @param idDemo
     * @return
     */
    List<PfCaseHistoryTagVo> listAllCaseHistoryTag(Long idDemo);

    /**
     * 所有评估表
     *
     * @param idDemo
     * @return
     */
    List<PfAssessTagVo> listAllAssessTag(Long idDemo);

}