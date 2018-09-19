package com.sm.pfprod.integration.system.set;

import com.sm.open.core.facade.model.param.pf.system.set.PfEmailSendParam;
import com.sm.open.core.facade.model.param.pf.system.set.PfEmailSetParam;
import com.sm.open.core.facade.model.result.pf.system.set.PfEmailSetResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.pf.system.set.PfSetFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SetClient {

    @Resource
    private PfSetFacade pfSetFacade;

    public CommonResult<Boolean> emailSet(PfEmailSetParam param) {
        return pfSetFacade.emailSet(param);
    }

    public CommonResult<PfEmailSetResult> selectEmailSet() {
        return pfSetFacade.selectEmailSet();
    }

    public CommonResult<Boolean> sendEmail(PfEmailSendParam param) {
        return pfSetFacade.sendEmail(param);
    }
}
