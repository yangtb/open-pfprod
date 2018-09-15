package com.sm.pfprod.service.system.monitor.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.result.pf.system.monitor.PfServerResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.pfprod.integration.system.mointor.MonitorClient;
import com.sm.pfprod.model.vo.monitor.PfServerVo;
import com.sm.pfprod.service.system.monitor.PfMonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfMonitorService")
public class PfMonitorServiceImpl implements PfMonitorService {

    @Resource
    private MonitorClient monitorClient;

    @Override
    public PfServerVo selectServerInfo() {
        CommonResult<PfServerResult> result = monitorClient.selectServerInfo();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfServerVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}
