package com.sm.pfprod.integration.home;

import com.sm.open.core.facade.model.param.pf.home.PfHomeParam;
import com.sm.open.core.facade.model.result.pf.home.PfHomeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.pf.home.PfHomeFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HomeClient {

    @Resource
    private PfHomeFacade pfHomeFacade;

    public CommonResult<PfHomeResult> selectHomeInfo(PfHomeParam param) {
        return pfHomeFacade.selectHomeInfo(param);
    }

}

