package com.sm.pfprod.service.biz.kb.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.kb.PfSaveAsMedParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.*;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.kb.KbPartClient;
import com.sm.pfprod.model.dto.biz.kb.PfSaveAsMedDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfKbPartService")
public class PfKbPartServiceImpl implements PfKbPartService {

    @Resource
    private KbPartClient kbPartClient;

    @Override
    public PageResult listKbPart(PfMedCaseDto dto) {
        PfPageResult<FaqMedCaseResult> result = kbPartClient.listKbPart(BeanUtil.convert(dto, PfMedCaseParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long addKbPart(FaqMedCase dto) {
        CommonResult<Long> result = kbPartClient.addKbPart(BeanUtil.convert(dto, FaqMedCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editKbPart(FaqMedCase dto) {
        CommonResult<Boolean> result = kbPartClient.editKbPart(BeanUtil.convert(dto, FaqMedCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbPart(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbPartClient.delKbPart(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listFaqMedCaseInques(PfPartCommonDto dto) {
        PfPageResult<FaqMedCaseInquesListResult> result = kbPartClient.listFaqMedCaseInques(BeanUtil.convert(dto, PfPartCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveFaqMedCaseInques(FaqMedCaseInquesList dto) {
        CommonResult<Long> result = kbPartClient.saveFaqMedCaseInques(BeanUtil.convert(dto, FaqMedCaseInquesListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delFaqMedCaseInques(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbPartClient.delFaqMedCaseInques(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseInquesList resetKbCons(FaqMedCaseInquesList dto) {
        CommonResult<FaqMedCaseInquesListResult> result = kbPartClient.resetKbCons(BeanUtil.convert(dto, FaqMedCaseInquesListParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseInquesList.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveKbText(FaqMedCaseText dto) {
        CommonResult<Boolean> result = kbPartClient.saveKbText(BeanUtil.convert(dto, FaqMedCaseTextParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseText selectKbText(Long idMedCase) {
        CommonResult<FaqMedCaseTextResult> result = kbPartClient.selectKbText(idMedCase);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseText.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveKbGuide(FaqMedCaseGuide dto) {
        CommonResult<Boolean> result = kbPartClient.saveKbGuide(BeanUtil.convert(dto, FaqMedCaseGuideParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseGuide selectKbGuide(Long idMedCase) {
        CommonResult<FaqMedCaseGuideResult> result = kbPartClient.selectKbGuide(idMedCase);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseGuide.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveKbPic(FaqMedCasePic dto) {
        CommonResult<Boolean> result = kbPartClient.saveKbPic(BeanUtil.convert(dto, FaqMedCasePicParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCasePic selectKbPic(Long idMedCase) {
        CommonResult<FaqMedCasePicResult> result = kbPartClient.selectKbPic(idMedCase);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCasePic.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveKbPat(FaqMedCasePatient dto) {
        CommonResult<Boolean> result = kbPartClient.saveKbPat(BeanUtil.convert(dto, FaqMedCasePatientParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCasePatient selectKbPat(Long idMedCase) {
        CommonResult<FaqMedCasePatientResult> result = kbPartClient.selectKbPat(idMedCase);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCasePatient.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listExams(PfPartCommonDto dto) {
        PfPageResult<FaqMedCaseInspectListResult> result = kbPartClient.listExams(BeanUtil.convert(dto, PfPartCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveExam(FaqMedCaseInspectList dto) {
        CommonResult<Long> result = kbPartClient.saveExam(BeanUtil.convert(dto, FaqMedCaseInspectListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbExam(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbPartClient.delKbExam(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseInspectList resetKbExam(FaqMedCaseInspectList dto) {
        CommonResult<FaqMedCaseInspectListResult> result = kbPartClient.resetKbExam(BeanUtil.convert(dto, FaqMedCaseInspectListParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseInspectList.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listChecks(PfPartCommonDto dto) {
        PfPageResult<FaqMedCaseBodyListResult> result = kbPartClient.listChecks(BeanUtil.convert(dto, PfPartCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveCheck(FaqMedCaseBodyList dto) {
        CommonResult<Long> result = kbPartClient.saveCheck(BeanUtil.convert(dto, FaqMedCaseBodyListparam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbCheck(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbPartClient.delKbCheck(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseBodyList resetKbCheck(FaqMedCaseBodyList dto) {
        CommonResult<FaqMedCaseBodyListResult> result = kbPartClient.resetKbCheck(BeanUtil.convert(dto, FaqMedCaseBodyListparam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseBodyList.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveFaqMedCaseBody(FaqMedCaseBody dto) {
        CommonResult<Boolean> result = kbPartClient.saveFaqMedCaseBody(BeanUtil.convert(dto, FaqMedCaseBodyParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedCaseBody selectFaqMedCaseBody(Long idMedCase) {
        CommonResult<FaqMedCaseBodyResult> result = kbPartClient.selectFaqMedCaseBody(idMedCase);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedCaseBody.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean bachAddCons(PfSaveAsMedDto dto) {
        CommonResult<Boolean> result = kbPartClient.bachAddCons(BeanUtil.convert(dto, PfSaveAsMedParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean bachAddCheck(PfSaveAsMedDto dto) {
        CommonResult<Boolean> result = kbPartClient.bachAddCheck(BeanUtil.convert(dto, PfSaveAsMedParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean bachAddExam(PfSaveAsMedDto dto) {
        CommonResult<Boolean> result = kbPartClient.bachAddExam(BeanUtil.convert(dto, PfSaveAsMedParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPreQuestion(FaqMedCaseInquesList dto) {
        PfPageResult<FaqMedCaseInquesListResult> result = kbPartClient.listPreQuestion(BeanUtil.convert(dto, FaqMedCaseInquesListParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

}
