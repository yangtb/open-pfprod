package com.sm.pfprod.service.system.monitor.impl;

import com.sm.pfprod.model.vo.monitor.PfServerVo;
import com.sm.pfprod.service.system.monitor.PfMonitorService;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("pfMonitorService")
public class PfMonitorServiceImpl implements PfMonitorService {

    @Override
    public PfServerVo selectServerInfo() {
        PfServerVo pfServerVo = new PfServerVo();
        // 获得系统属性集
        Properties props=System.getProperties();
        pfServerVo.setOsName(props.getProperty("os.name"));
        pfServerVo.setOsVersion(props.getProperty("os.version"));
        pfServerVo.setJavaVersion(props.getProperty("java.version"));
        pfServerVo.setUserDir(props.getProperty("user.dir"));

        Runtime runtime = Runtime.getRuntime();
        pfServerVo.setCpuNum(runtime.availableProcessors());
        pfServerVo.setTotalMemory(runtime.totalMemory());
        pfServerVo.setFreeMemory(runtime.freeMemory());
        return pfServerVo;
    }
}
