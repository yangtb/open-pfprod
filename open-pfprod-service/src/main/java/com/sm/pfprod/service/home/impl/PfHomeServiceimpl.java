package com.sm.pfprod.service.home.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.home.PfHomeParam;
import com.sm.open.core.facade.model.result.pf.home.PfHomeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.pfprod.integration.home.HomeClient;
import com.sm.pfprod.model.dto.home.PfHomeDto;
import com.sm.pfprod.model.vo.home.PfHomeVo;
import com.sm.pfprod.service.home.PfHomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfHomeService")
public class PfHomeServiceimpl implements PfHomeService {

    @Resource
    private HomeClient homeClient;

    @Override
    public PfHomeVo selectHomeInfo(PfHomeDto dto) {
        CommonResult<PfHomeResult> result = homeClient.selectHomeInfo(BeanUtil.convert(dto, PfHomeParam.class));
        if (result != null && result.getIsSuccess()) {
            return PfHomeBeanUtil.convert(result.getContent());
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
