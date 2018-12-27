package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.paper.PfAddCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.ExmTestplanParam;
import com.sm.open.core.facade.model.param.pf.biz.tests.plan.PfTestPlanParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.plan.ExmTestplanDetailResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.plan.ExmTestplanMedicalrecResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.plan.ExmTestplanResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.plan.ExmTestplanStudentResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestPlanClient;
import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.model.entity.ExmTestplanMedicalrec;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.tests.PfTestPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<PfCommonZtreeVo> listCaseTree(PfCatalogueTreeDto dto) {
        CommonResult<List<PfCommonZtreeResult>> result = testPlanClient.listCaseTree(BeanUtil.convert(dto, PfCatalogueTreeParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPlanItem(PfTestPlanDto dto) {
        PfPageResult<ExmTestplanMedicalrecResult> result = testPlanClient.listPlanItem(BeanUtil.convert(dto, PfTestPlanParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addPlanItem(PfAddCaseDto dto) {
        CommonResult<Boolean> result = testPlanClient.addPlanItem(BeanUtil.convert(dto, PfAddCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPlanItem(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPlanClient.delPlanItem(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updatePlanItemSort(ExmTestplanMedicalrec dto) {
        CommonResult<Boolean> result = testPlanClient.updatePlanItemSort(BeanUtil.convert(dto, ExmTestplanMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfCommonZtreeVo> listStudentTree(PfCatalogueTreeDto dto) {
        CommonResult<List<PfCommonZtreeResult>> result = testPlanClient.listStudentTree(BeanUtil.convert(dto, PfCatalogueTreeParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPlanStudent(PfTestPlanDto dto) {
        PfPageResult<ExmTestplanStudentResult> result = testPlanClient.listPlanStudent(BeanUtil.convert(dto, PfTestPlanParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addPlanStudent(PfAddCaseDto dto) {
        CommonResult<Boolean> result = testPlanClient.addPlanStudent(BeanUtil.convert(dto, PfAddCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPlanStudent(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPlanClient.delPlanStudent(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPlanDetail(PfTestPlanDto dto) {
        PfPageResult<ExmTestplanDetailResult> result = testPlanClient.listPlanDetail(BeanUtil.convert(dto, PfTestPlanParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean generatePlan(Long idTestplan) {
        CommonResult<Boolean> result = testPlanClient.generatePlan(idTestplan);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
