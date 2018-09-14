package com.sm.pfprod.facade.monitor;

import com.sm.pfprod.model.vo.monitor.PfServerVo;
import com.sm.pfprod.service.system.monitor.PfMonitorService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("pfMonitorFacade")
public class PfMonitorFacadeImpl implements PfMonitorFacade {

    @Resource
    private PfMonitorService pfMonitorService;

    @Override
    public PfServerVo selectServerInfo() {
        return pfMonitorService.selectServerInfo();
    }
}
