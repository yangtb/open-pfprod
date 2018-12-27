package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.paper.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.tests.paper.ExmTestpaperResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestPaperFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TestPaperClient {

    @Resource
    private PfTestPaperFacade pfTestPaperFacade;

    public CommonResult<List<PfCommonZtreeResult>> listPaperClassifyTree(Long idOrg) {
        return pfTestPaperFacade.listPaperClassifyTree(idOrg);
    }

    public CommonResult<Long> savePaperClassify(ExmTestpaperCaParam param) {
        return pfTestPaperFacade.savePaperClassify(param);
    }

    public CommonResult<Boolean> delPaperClassify(PfBachChangeStatusParam param) {
        return pfTestPaperFacade.delPaperClassify(param);
    }

    public CommonResult<List<ExmTestpaperResult>> listAllPaper(PfTestPaperParam param) {
        return pfTestPaperFacade.listAllPaper(param);
    }

    public PfPageResult listPaper(PfTestPaperParam param) {
        return pfTestPaperFacade.listPaper(param);
    }

    public CommonResult<Long> savePaper(ExmTestpaperParam param) {
        return pfTestPaperFacade.savePaper(param);
    }

    public CommonResult<Boolean> delPaper(PfBachChangeStatusParam param) {
        return pfTestPaperFacade.delPaper(param);
    }

    public CommonResult<List<PfCommonZtreeResult>> listCaseTree(PfCatalogueTreeParam param) {
        return pfTestPaperFacade.listCaseTree(param);
    }

    public PfPageResult listPaperItem(PfTestPaperParam param) {
        return pfTestPaperFacade.listPaperItem(param);
    }

    public CommonResult<Boolean> addPaperItem(PfAddCaseParam param) {
        return pfTestPaperFacade.addPaperItem(param);
    }

    public CommonResult<Boolean> delPaperItem(PfBachChangeStatusParam param) {
        return pfTestPaperFacade.delPaperItem(param);
    }

    public CommonResult<Boolean> updatePaperItemSort(ExmTestpaperMedicalrecParam param) {
        return pfTestPaperFacade.updatePaperItemSort(param);
    }
}
