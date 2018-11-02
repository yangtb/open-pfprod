package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.PfTestPlanParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.tests.plan.ExmTestplanResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestPlanClient;
import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.tests.PfTestPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfTestPlanService")
public class PfTestPlanServiceImpl implements PfTestPlanService {

    @Resource
    private TestPlanClient testPlanClient;

    @Override
    public PageResult listPlans(PfTestPlanDto dto) {
        PfPageResult<ExmTestplanResult> result = testPlanClient.listPlans(BeanUtil.convert(dto, PfTestPlanParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long savePlan(ExmTestplan dto) {
        CommonResult<Long> result = testPlanClient.savePlan(BeanUtil.convert(dto, ExmTestplanParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPlan(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPlanClient.delPlan(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
