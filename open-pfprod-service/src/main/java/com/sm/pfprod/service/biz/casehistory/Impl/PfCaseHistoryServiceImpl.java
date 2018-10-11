package com.sm.pfprod.service.biz.casehistory.Impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.casehistory.FaqMedicalrecCaParam;
import com.sm.open.core.facade.model.param.pf.biz.casehistory.FaqMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.casehistory.PfCaseHistoryParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.casehistory.FaqMedicalrecResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.casehistory.CaseHistoryClient;
import com.sm.pfprod.model.dto.biz.casehistory.PfCaseHistoryDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqMedicalrec;
import com.sm.pfprod.model.entity.FaqMedicalrecCa;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.casehistory.PfCaseHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfCaseHistoryService")
public class PfCaseHistoryServiceImpl implements PfCaseHistoryService {

    @Resource
    private CaseHistoryClient caseHistoryClient;

    @Override
    public List<PfCommonZtreeVo> listClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = caseHistoryClient.listClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addClassify(FaqMedicalrecCa dto) {
        CommonResult<Long> result = caseHistoryClient.addClassify(BeanUtil.convert(dto, FaqMedicalrecCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editClassify(FaqMedicalrecCa dto) {
        CommonResult<Boolean> result = caseHistoryClient.editClassify(BeanUtil.convert(dto, FaqMedicalrecCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = caseHistoryClient.delClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTemplate(PfCaseHistoryDto dto) {
        PfPageResult<FaqMedicalrecResult> result = caseHistoryClient.listTemplate(BeanUtil.convert(dto, PfCaseHistoryParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addTemplate(FaqMedicalrec dto) {
        CommonResult<Boolean> result = caseHistoryClient.addTemplate(BeanUtil.convert(dto, FaqMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editTemplate(FaqMedicalrec dto) {
        CommonResult<Boolean> result = caseHistoryClient.editTemplate(BeanUtil.convert(dto, FaqMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delTemplate(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = caseHistoryClient.delTemplate(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
