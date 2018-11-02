package com.sm.pfprod.service.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestpaperMedicalrec;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.model.entity.ExmTestplanMedicalrec;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;

import java.util.List;

/**
 * @ClassName: PfTestPlanService
 * @Description: 测试计划
 * @Author yangtongbin
 * @Date 2018/11/2
 */
public interface PfTestPlanService {

    /**
     * 计划列表
     *
     * @param dto
     * @return
     */
    PageResult listPlans(PfTestPlanDto dto);

    /**
     * 新增计划
     *
     * @param dto
     * @return
     */
    Long savePlan(ExmTestplan dto);

    /**
     * 删除计划
     *
     * @param dto
     * @return
     */
    boolean delPlan(PfBachChangeStatusDto dto);

    /**
     * 病例tree
     *
     * @param dto
     * @return
     */
    List<PfCommonZtreeVo> listCaseTree(PfCatalogueTreeDto dto);

    /**
     * 试题清单列表
     *
     * @param dto
     * @return
     */
    PageResult listPlanItem(PfTestPlanDto dto);

    /**
     * 添加试题清单
     *
     * @param dto
     * @return
     */
    boolean addPlanItem(PfAddCaseDto dto);

    /**
     * 删除试题清单
     *
     * @param dto
     * @return
     */
    boolean delPlanItem(PfBachChangeStatusDto dto);

    /**
     * 更新排序
     *
     * @param dto
     * @return
     */
    boolean updatePlanItemSort(ExmTestplanMedicalrec dto);

}
