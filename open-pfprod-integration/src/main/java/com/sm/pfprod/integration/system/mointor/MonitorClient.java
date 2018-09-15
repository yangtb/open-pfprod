package com.sm.pfprod.integration.system.mointor;

import com.sm.open.core.facade.model.result.pf.system.monitor.PfServerResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.pf.system.monitor.PfMonitorFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MonitorClient {

    @Resource
    private PfMonitorFacade pfMonitorFacade;


    public CommonResult<PfServerResult> selectServerInfo() {
        return pfMonitorFacade.selectServerInfo();
    }
}
