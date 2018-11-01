package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.paper.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.paper.ExmTestpaperMedicalrecResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.paper.ExmTestpaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestPaperClient;
import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPaperDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestpaper;
import com.sm.pfprod.model.entity.ExmTestpaperCa;
import com.sm.pfprod.model.entity.ExmTestpaperMedicalrec;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.tests.PfTestPaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfTestPaperService")
public class PfTestPaperServiceImpl implements PfTestPaperService {

    @Resource
    private TestPaperClient testPaperClient;

    @Override
    public List<PfCommonZtreeVo> listPaperClassifyTree(Long idOrg) {
        CommonResult<List<PfCommonZtreeResult>> result = testPaperClient.listPaperClassifyTree(idOrg);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long savePaperClassify(ExmTestpaperCa dto) {
        CommonResult<Long> result = testPaperClient.savePaperClassify(BeanUtil.convert(dto, ExmTestpaperCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPaperClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPaperClient.delPaperClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPaper(PfTestPaperDto dto) {
        PfPageResult<ExmTestpaperResult> result = testPaperClient.listPaper(BeanUtil.convert(dto, PfTestPaperParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long savePaper(ExmTestpaper dto) {
        CommonResult<Long> result = testPaperClient.savePaper(BeanUtil.convert(dto, ExmTestpaperParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPaper(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPaperClient.delPaper(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfCommonZtreeVo> listCaseTree(PfCatalogueTreeDto dto) {
        CommonResult<List<PfCommonZtreeResult>> result = testPaperClient.listCaseTree(BeanUtil.convert(dto, PfCatalogueTreeParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listPaperItem(PfTestPaperDto dto) {
        PfPageResult<ExmTestpaperMedicalrecResult> result = testPaperClient.listPaperItem(BeanUtil.convert(dto, PfTestPaperParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addPaperItem(PfAddCaseDto dto) {
        CommonResult<Boolean> result = testPaperClient.addPaperItem(BeanUtil.convert(dto, PfAddCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPaperItem(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = testPaperClient.delPaperItem(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updatePaperItemSort(ExmTestpaperMedicalrec dto) {
        CommonResult<Boolean> result = testPaperClient.updatePaperItemSort(BeanUtil.convert(dto, ExmTestpaperMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
