package com.sm.pfprod.service.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.model.result.PageResult;

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

}
