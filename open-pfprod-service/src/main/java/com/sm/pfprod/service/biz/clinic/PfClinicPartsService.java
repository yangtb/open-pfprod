package com.sm.pfprod.service.biz.clinic;

import com.sm.pfprod.model.dto.biz.clinic.parts.PfClinicPartsDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasAlgorithm;
import com.sm.pfprod.model.entity.BasEvaAsse;
import com.sm.pfprod.model.entity.BasMedAsse;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfClinicPartsService
 * @Description: 临床模板组件定义
 * @Author yangtongbin
 * @Date 2018/10/12
 */
public interface PfClinicPartsService {

    /**
     * 组件列表
     *
     * @param dto
     * @return
     */
    PageResult listParts(PfClinicPartsDto dto);

    /**
     * 新增组件定义
     *
     * @param dto
     * @return
     */
    boolean addPart(BasMedAsse dto);

    /**
     * 编辑组件定义
     *
     * @param dto
     * @return
     */
    boolean editPart(BasMedAsse dto);

    /**
     * 删除组件定义
     *
     * @param dto
     * @return
     */
    boolean delPart(PfBachChangeStatusDto dto);

    /**
     * 评估表列表
     *
     * @param dto
     * @return
     */
    PageResult listSheet(PfClinicPartsDto dto);

    /**
     * 新增评估表定义
     *
     * @param dto
     * @return
     */
    boolean addSheet(BasEvaAsse dto);

    /**
     * 编辑评估表定义
     *
     * @param dto
     * @return
     */
    boolean editSheet(BasEvaAsse dto);

    /**
     * 删除评估表定义
     *
     * @param dto
     * @return
     */
    boolean delSheet(PfBachChangeStatusDto dto);

    /**
     * 算法列表
     *
     * @param dto
     * @return
     */
    PageResult listAlgorithm(PfClinicPartsDto dto);

    /**
     * 新增评估表定义
     *
     * @param dto
     * @return
     */
    boolean addAlgorithm(BasAlgorithm dto);

    /**
     * 编辑算法定义
     *
     * @param dto
     * @return
     */
    boolean editAlgorithm(BasAlgorithm dto);

    /**
     * 删除算法定义
     *
     * @param dto
     * @return
     */
    boolean delAlgorithm(PfBachChangeStatusDto dto);
}