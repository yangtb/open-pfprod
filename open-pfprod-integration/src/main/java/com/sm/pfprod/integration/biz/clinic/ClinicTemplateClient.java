package com.sm.pfprod.integration.biz.clinic;

import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoCaParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoTagParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.PfClinicTemplateParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PageResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.clinic.PfClinicTemplateFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ClinicTemplateClient {

    @Resource
    private PfClinicTemplateFacade pfClinicTemplateFacade;

    public CommonResult<List<PfCommonZtreeResult>> listClassifyTree() {
        return pfClinicTemplateFacade.listClassifyTree();
    }

    public CommonResult<Long> addClassify(BasDemoCaParam param) {
        return pfClinicTemplateFacade.addClassify(param);
    }

    public CommonResult<Boolean> editClassify(BasDemoCaParam param) {
        return pfClinicTemplateFacade.editClassify(param);
    }

    public CommonResult<Boolean> delClassify(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delClassify(param);
    }

    public PfPageResult listTemplate(PfClinicTemplateParam param) {
        return pfClinicTemplateFacade.listTemplate(param);
    }

    public CommonResult<Boolean> addTemplate(BasDemoParam param) {
        return pfClinicTemplateFacade.addTemplate(param);
    }

    public CommonResult<Boolean> editTemplate(BasDemoParam param) {
        return pfClinicTemplateFacade.editTemplate(param);
    }

    public CommonResult<Boolean> delTemplate(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delTemplate(param);
    }

    public PfPageResult listTag(PfClinicTemplateParam param) {
        return pfClinicTemplateFacade.listTag(param);
    }

    public CommonResult<Boolean> delTag(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delTag(param);
    }

    public CommonResult<Long> saveTag(BasDemoTagParam param) {
        return pfClinicTemplateFacade.saveTag(param);
    }

}
