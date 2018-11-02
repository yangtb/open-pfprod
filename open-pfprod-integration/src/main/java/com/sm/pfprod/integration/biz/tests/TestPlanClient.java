package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.PfTestPlanParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestPlanFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestPlanClient {

    @Resource
    private PfTestPlanFacade pfTestPlanFacade;

    public PfPageResult listPlans(PfTestPlanParam param) {
        return pfTestPlanFacade.listPlans(param);
    }

    public CommonResult<Long> savePlan(ExmTestplanParam param) {
        return pfTestPlanFacade.savePlan(param);
    }

    public CommonResult<Boolean> delPlan(PfBachChangeStatusParam param) {
        return pfTestPlanFacade.delPlan(param);
    }

}
