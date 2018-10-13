package com.sm.pfprod.integration.biz.clinic;

import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasAlgorithmParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasEvaAsseParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasMedAsseParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.PfClinicPartsParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.clinic.PfClinicPartsFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClinicPartsClient {

    @Resource
    private PfClinicPartsFacade pfClinicPartsFacade;


    public PfPageResult listParts(PfClinicPartsParam param) {
        return pfClinicPartsFacade.listParts(param);
    }


    public CommonResult<Boolean> addPart(BasMedAsseParam param) {
        return pfClinicPartsFacade.addPart(param);
    }


    public CommonResult<Boolean> editPart(BasMedAsseParam param) {
        return pfClinicPartsFacade.editPart(param);
    }


    public CommonResult<Boolean> delPart(PfBachChangeStatusParam param) {
        return pfClinicPartsFacade.delPart(param);
    }


    public PfPageResult listSheet(PfClinicPartsParam param) {
        return pfClinicPartsFacade.listSheet(param);
    }


    public CommonResult<Boolean> addSheet(BasEvaAsseParam param) {
        return pfClinicPartsFacade.addSheet(param);
    }


    public CommonResult<Boolean> editSheet(BasEvaAsseParam param) {
        return pfClinicPartsFacade.editSheet(param);
    }


    public CommonResult<Boolean> delSheet(PfBachChangeStatusParam param) {
        return pfClinicPartsFacade.delSheet(param);
    }


    public PfPageResult listAlgorithm(PfClinicPartsParam param) {
        return pfClinicPartsFacade.listAlgorithm(param);
    }


    public CommonResult<Boolean> addAlgorithm(BasAlgorithmParam param) {
        return pfClinicPartsFacade.addAlgorithm(param);
    }


    public CommonResult<Boolean> editAlgorithm(BasAlgorithmParam param) {
        return pfClinicPartsFacade.editAlgorithm(param);
    }


    public CommonResult<Boolean> delAlgorithm(PfBachChangeStatusParam param) {
        return pfClinicPartsFacade.delAlgorithm(param);
    }

}
