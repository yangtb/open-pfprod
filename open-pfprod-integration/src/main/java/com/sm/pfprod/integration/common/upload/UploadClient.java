package com.sm.pfprod.integration.common.upload;

import com.sm.open.core.facade.model.param.pf.common.upload.BasMediaParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.pf.common.upload.PfUploadFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UploadClient {

    @Resource
    private PfUploadFacade pfUploadFacade;

    public CommonResult<Long> addBasMedia(BasMediaParam param) {
        return pfUploadFacade.addBasMedia(param);
    }

}
