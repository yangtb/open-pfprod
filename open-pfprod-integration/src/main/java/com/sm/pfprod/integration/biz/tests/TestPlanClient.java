package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.paper.PfAddCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.PfTestPlanParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestPlanFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    public CommonResult<List<PfCommonZtreeResult>> listCaseTree(PfCatalogueTreeParam param) {
        return pfTestPlanFacade.listCaseTree(param);
    }

    public PfPageResult listPlanItem(PfTestPlanParam param) {
        return pfTestPlanFacade.listPlanItem(param);
    }

    public CommonResult<Boolean> addPlanItem(PfAddCaseParam param) {
        return pfTestPlanFacade.addPlanItem(param);
    }

    public CommonResult<Boolean> delPlanItem(PfBachChangeStatusParam param) {
        return pfTestPlanFacade.delPlanItem(param);
    }

    public CommonResult<Boolean> updatePlanItemSort(ExmTestplanMedicalrecParam param) {
        return pfTestPlanFacade.updatePlanItemSort(param);
    }

    public CommonResult<List<PfCommonZtreeResult>> listStudentTree(PfCatalogueTreeParam param) {
        return pfTestPlanFacade.listStudentTree(param);
    }

    public PfPageResult listPlanStudent(PfTestPlanParam param) {
        return pfTestPlanFacade.listPlanStudent(param);
    }

    public CommonResult<Boolean> addPlanStudent(PfAddCaseParam param) {
        return pfTestPlanFacade.addPlanStudent(param);
    }

    public CommonResult<Boolean> delPlanStudent(PfBachChangeStatusParam param) {
        return pfTestPlanFacade.delPlanStudent(param);
    }

    public PfPageResult listPlanDetail(PfTestPlanParam param) {
        return pfTestPlanFacade.listPlanDetail(param);
    }

    public CommonResult<Boolean> generatePlan(Long idTestplan) {
        return pfTestPlanFacade.generatePlan(idTestplan);
    }

}
