package com.sm.pfprod.service.common.upload.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.common.upload.BasMediaParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.pfprod.integration.common.upload.UploadClient;
import com.sm.pfprod.model.entity.BasMedia;
import com.sm.pfprod.service.common.upload.PfUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfUploadService")
public class PfUploadServiceImpl implements PfUploadService {

    @Resource
    private UploadClient uploadClient;

    @Override
    public Long addBasMedia(BasMedia dto) {
        CommonResult<Long> result = uploadClient.addBasMedia(BeanUtil.convert(dto, BasMediaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());

    }
}
