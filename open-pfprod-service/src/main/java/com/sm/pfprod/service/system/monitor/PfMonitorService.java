package com.sm.pfprod.service.system.monitor;

import com.sm.pfprod.model.vo.monitor.PfServerVo;

/**
 * @ClassName: PfMonitorFacade
 * @Description: 监控接口
 * @Author yangtongbin
 * @Date 2018/9/5 11:31
 */
public interface PfMonitorService {

    /**
     * 查询服务器信息
     *
     * @return
     */
    PfServerVo selectServerInfo();
}
