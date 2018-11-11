package com.sm.pfprod.integration.biz.clinic;

import com.sm.open.core.facade.model.param.pf.biz.clinic.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.*;
import com.sm.open.core.facade.model.rpc.CommonResult;
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

    public PfPageResult listCaseHistoryTag(PfClinicTemplateParam param) {
        return pfClinicTemplateFacade.listCaseHistoryTag(param);
    }

    public CommonResult<Boolean> delCaseHistoryTag(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delCaseHistoryTag(param);
    }

    public CommonResult<Long> saveCaseHistoryTag(BasMedicalTagParam param) {
        return pfClinicTemplateFacade.saveCaseHistoryTag(param);
    }

    public PfPageResult listSheetTag(PfClinicTemplateParam param) {
        return pfClinicTemplateFacade.listSheetTag(param);
    }

    public CommonResult<Boolean> delSheetTag(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delSheetTag(param);
    }

    public CommonResult<Long> saveSheetTag(BasEvaTagParam param) {
        return pfClinicTemplateFacade.saveSheetTag(param);
    }

    public CommonResult<List<BasDemoResult>> listAllBasDemo() {
        return pfClinicTemplateFacade.listAllBasDemo();
    }

    public CommonResult<List<BasDemoTagResult>> listTagByIdDemo(Long idDemo) {
        return pfClinicTemplateFacade.listTagByIdDemo(idDemo);
    }

    public CommonResult<List<PfCommonZtreeResult>> listDimensionTree(Long idDemo) {
        return pfClinicTemplateFacade.listDimensionTree(idDemo);
    }

    public CommonResult<Boolean> delDimensionTag(PfBachChangeStatusParam param) {
        return pfClinicTemplateFacade.delDimensionTag(param);
    }

    public CommonResult<Long> saveDimensionTag(BasDemoAssesParam param) {
        return pfClinicTemplateFacade.saveDimensionTag(param);
    }
    public CommonResult<BasDemoAssesResult> selectDimensionTagInfo(Long idDimemsion){
        return pfClinicTemplateFacade.selectDimensionTagInfo(idDimemsion);
    }

    public CommonResult<List<PfCaseHistoryTagResult>> listAllCaseHistoryTag(Long idDemo) {
        return pfClinicTemplateFacade.listAllCaseHistoryTag(idDemo);
    }

    public CommonResult<List<PfAssessTagResult>> listAllAssessTag(Long idDemo) {
        return pfClinicTemplateFacade.listAllAssessTag(idDemo);
    }
}
