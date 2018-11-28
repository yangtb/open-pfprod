package com.sm.pfprod.service.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfKbPartService
 * @Description: 病例组件用例
 * @Author yangtongbin
 * @Date 2018/10/17
 */
public interface PfKbPartService {

    /**
     * 病例组件用例列表
     *
     * @param dto
     * @return
     */
    PageResult listKbPart(PfMedCaseDto dto);

    /**
     * 新增病例组件用例
     *
     * @param dto
     * @return
     */
    Long addKbPart(FaqMedCase dto);

    /**
     * 编辑病例组件用例
     *
     * @param dto
     * @return
     */
    boolean editKbPart(FaqMedCase dto);

    /**
     * 删除病例组件用例
     *
     * @param dto
     * @return
     */
    boolean delKbPart(PfBachChangeStatusDto dto);

    /**
     * 问诊_问题明细
     *
     * @param dto
     * @return
     */
    PageResult listFaqMedCaseInques(PfPartCommonDto dto);

    /**
     * 保存问诊问题
     *
     * @param dto
     * @return
     */
    Long saveFaqMedCaseInques(FaqMedCaseInquesList dto);

    /**
     * 删除问诊问题
     *
     * @param dto
     * @return
     */
    boolean delFaqMedCaseInques(PfBachChangeStatusDto dto);

    /**
     * 重载咨询问题
     *
     * @param dto
     * @return
     */
    FaqMedCaseInquesList resetKbCons(FaqMedCaseInquesList dto);

    /**
     * 组件 - add文本
     *
     * @param dto
     * @return
     */
    boolean saveKbText(FaqMedCaseText dto);

    /**
     * 查询文本信息
     *
     * @param idMedCase
     * @return
     */
    FaqMedCaseText selectKbText(Long idMedCase);

    /**
     * 组件 - add图片
     *
     * @param dto
     * @return
     */
    boolean saveKbPic(FaqMedCasePic dto);

    /**
     * 查询图片信息
     *
     * @param idMedCase
     * @return
     */
    FaqMedCasePic selectKbPic(Long idMedCase);

    /**
     * 组件 - add患者
     *
     * @param dto
     * @return
     */
    boolean saveKbPat(FaqMedCasePatient dto);

    /**
     * 查询患者信息
     *
     * @param idMedCase
     * @return
     */
    FaqMedCasePatient selectKbPat(Long idMedCase);

    /**
     * 检验列表
     *
     * @param dto
     * @return
     */
    PageResult listExams(PfPartCommonDto dto);

    /**
     * 保存检验问题
     *
     * @param dto
     * @return
     */
    Long saveExam(FaqMedCaseInspectList dto);

    /**
     * 删除检验组件用例
     *
     * @param dto
     * @return
     */
    boolean delKbExam(PfBachChangeStatusDto dto);

    /**
     * 重载检验项目
     *
     * @param dto
     * @return
     */
    FaqMedCaseInspectList resetKbExam(FaqMedCaseInspectList dto);

    /**
     * 检查列表
     *
     * @param dto
     * @return
     */
    PageResult listChecks(PfPartCommonDto dto);

    /**
     * 保存检查
     *
     * @param dto
     * @return
     */
    Long saveCheck(FaqMedCaseBodyList dto);

    /**
     * 删除检查
     *
     * @param dto
     * @return
     */
    boolean delKbCheck(PfBachChangeStatusDto dto);

    /**
     * 重载检查
     *
     * @param dto
     * @return
     */
    FaqMedCaseBodyList resetKbCheck(FaqMedCaseBodyList dto);

    /**
     * 保存检查图片
     *
     * @param dto
     * @return
     */
    boolean saveFaqMedCaseBody(FaqMedCaseBody dto);

    /**
     * 查询检查图片
     *
     * @param idMedCase
     * @return
     */
    FaqMedCaseBody selectFaqMedCaseBody(Long idMedCase);

    /**
     * 批量添加问诊
     *
     * @param dto
     * @return
     */
    boolean bachAddCons(PfCommonListDto dto);

    /**
     * 批量添加体格检查
     *
     * @param dto
     * @return
     */
    boolean bachAddCheck(PfCommonListDto dto);

    /**
     * 批量添加辅助检查
     *
     * @param dto
     * @return
     */
    boolean bachAddExam(PfCommonListDto dto);

}
